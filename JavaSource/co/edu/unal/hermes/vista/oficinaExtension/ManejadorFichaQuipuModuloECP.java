package co.edu.unal.hermes.vista.oficinaExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorFichaQuipuModuloECP extends ManejadorBase{
	//ATRIBUTOS
	
	private Convocatoria moduloActual;		
	
	private String activdadECP;
	private String areaTematica;
	private String ejeTematico;
	private String codAreaTematica;
	private String grupoBusqueda;
	private String planGlobal;
	
	private Sede sede; // = "2";  Bogotá
	private Dependencia facultad; // = "2055"; Ingeniería
	private String departamento; // = "2368";  Sistemas
	private String areaResponsable;
	protected List<SelectItem> sedeItem;
	protected List<SelectItem> facultadItem;
	protected List<SelectItem> departamentoItem;
	boolean mostrarFacultad = false;
	boolean mostrarDepartamento = false;
	private List listaDependencias;
	private boolean mostrarPagListaDpn;
	
	private String antecedente;
	private List listaAntecedentes;
	private boolean mostrarPagListaAnt;
	
	private String tpProd_1;
	private String tpProd_2;
	private String tpProd_3;
	private String nombreProd;
	private String tpProd;
	private String tipoProdItem;
	private String cantidadProd;
	private String fecEntregaProd;
	private List listaProds;
	
	//prod TODO 
	private List listaProductosItem;
	private UISelectMany selectProducto;
	private String avisoProductos;
	private String[] listaProductoSeleccionados;
	private HashMap listaTemporalProductoSeleccionados;
	private HashMap listaTemporalCompromisosSeleccionados;
	private List listaproductosTipo;
	private List productoTipoNivel1;
	private List productoTipoNivel2;
	private List productoTipoNivel3;
	private boolean[] readOnlySelect;
	private String[] seleccionSelect;
	private ProductoTipo productoActual;

	// CONSTRUCTOR	
	public ManejadorFichaQuipuModuloECP(){
		
		sesion.getAttribute("moduloECP");				
		
		
		sede = new Sede();
		facultad = new Dependencia();
		listaDependencias = new ArrayList<Dependencia>();
		listaAntecedentes = new ArrayList<String>();
		
		mostrarPagListaDpn = false;
		
		cargarSedes(); //TODO AL GUARDAR INGRESAR AREA RESPONSABLE
		cargarListaProductosConvocatoria();		
		
	}
	
	//METODOS
	public List<String> completeGroup(String query) {  
        List<String> results = new ArrayList<String>();
        String consulta; 	    
        consulta = "select ff from FuenteFinanciacion ff where  ff.descripcion like upper('" + query + "%')";
        List lista = servicioGeneral.obtenerObjetos(consulta);	
        for (int i=0; i<lista.size();i++){ 
        	FuenteFinanciacion entidad = (FuenteFinanciacion) lista.get(i);
            results.add(new String(entidad.getDescripcion()));           
        }        
        return results;  
    }
	
	private void cargarSedes() {				
		sedeItem = new ArrayList<SelectItem>();		
		
		String consulta = "select dd from Dependencia dd where dd.esSede = 'Y'";
		List lista = servicioGeneral.obtenerObjetos(consulta);
		
		sedeItem.add(0, new SelectItem("Seleccione una sede"));
		
		for (int i=0; i<lista.size();i++){
			Dependencia sede = (Dependencia) lista.get(i);	    	
			sedeItem.add(new SelectItem(sede.getId(), sede.getNombre()));
		}
		
		mostrarDepartamento = false;
	}
	
	public void cargarFacultades(){			    
		facultadItem = new ArrayList<SelectItem>();
		departamentoItem = new ArrayList<SelectItem>();
		mostrarDepartamento = false;
		
		facultadItem.add(0, new SelectItem("Seleccione una facultad"));				
		
		if(!sede.equals("Seleccione una sede")){
			
			mostrarFacultad = true;			
		
			String consulta = "select dd from Dependencia dd where dd.sede.id = " + sede.getId() + 
							  " and dd.esFacultad = 'Y'";
			List lista = servicioGeneral.obtenerObjetos(consulta);						
	
			for (int i=0; i<lista.size();i++){
				Dependencia facultad = (Dependencia) lista.get(i);	    	
				facultadItem.add(new SelectItem(facultad.getId(), facultad.getNombre()));
			}
		}else
			mostrarFacultad = false;
	}	
	
	public void cargarDepartamentos(){
		departamentoItem = new ArrayList<SelectItem>();		

		if(!facultad.equals("Seleccione una facultad")){						

			String consulta = "select dd from Dependencia dd where dd.facultad.id =" + facultad.getId() +
					" and dd.esDepartamento = 'Y'";
			List lista = servicioGeneral.obtenerObjetos(consulta);

			if(lista.size() > 0){
				departamentoItem.add(0, new SelectItem("Seleccione un departamento"));

				for (int i=0; i<lista.size();i++){
					Dependencia depto = (Dependencia) lista.get(i);	    	
					departamentoItem.add(new SelectItem(depto.getId(), depto.getNombre()));
				}
				mostrarDepartamento = true;
			}else{
				mostrarDepartamento = false;
				departamento = "";
			}

		}else{
			mostrarDepartamento = false;
			departamento = "";
			}
	}
	
	public void agregarDependencias(){
		Dependencia d = new Dependencia();
		
		String consulta = "select dd from Dependencia dd where dd.id =" + departamento +
				" and dd.esDepartamento = 'Y'";
		List lista = servicioGeneral.obtenerObjetos(consulta);
		
		d = (Dependencia)lista.get(0);
		
		listaDependencias.add(d);//HACER ALGO COMO DEPENDENCIA PROYECTO
		
		if(listaDependencias.size() >= 10)
			mostrarPagListaDpn = true;
		else 
			mostrarPagListaDpn = false;
	}
	
	public void agregarAntecedente(){						
		if(!antecedente.equals("") || !antecedente.isEmpty()){
			listaAntecedentes.add(antecedente);
			antecedente="";
			if(listaAntecedentes.size() >= 10)	
				mostrarPagListaAnt = true;
			else 
				mostrarPagListaAnt = false;			
		}else{
			FacesContext.getCurrentInstance().addMessage("mensajeErrorCantidad", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Por favor ingrese un antecedente", ""));
		}
	}
	
	/*public void agregarProducto(){
	    int c;
	    try{
	        c = Integer.parseInt(cantidadProd);
	        //ProyectoProducto productoProyecto = new ProyectoProducto();
		    ProductoTipo p = buscarProductoTipoNivel3(productoNivel3);
		    //productoProyecto.setProducto(p);
		    //productoProyecto.setCantidad(c);		    		    
		    
		    Set listaProductos = proyectoActual.getProductosProyecto();
		    boolean existeProducto = existeProductoEnSet(listaProductos,productoProyecto);
		    if(!existeProducto){
			 	proyectoActual.adicionarProductoProyecto(productoProyecto);
			        cantidad = "1";
			        descripcion = "";
			        mensajeErrorCantidad = "";
			        mensajeError = "";
		    }
		    
	       
	    }catch(Exception e){
	    	FacesContext.getCurrentInstance().addMessage("mensajeErrorCantidad", new FacesMessage(FacesMessage.SEVERITY_ERROR,"La cantidad no es un número valido", ""));
	        mensajeErrorCantidad = "La cantidad no es un número valido";
	    }
	    
	}
	
	*public boolean existeProductoEnSet(Set productos,  ProyectoProducto productoProyecto ){
		Iterator it = productos.iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if( obj instanceof ProyectoProducto ){
				if( ((ProyectoProducto)obj).getProducto().getNombre().toUpperCase().equals(productoProyecto.getProducto().getNombre().toUpperCase())){
					return true;
				}
			}
		}
		return false;
	}

	public void eliminarProducto(){
		proyectoActual.borrarProductoProyecto(productoSeleccionado);
	}
	
    public void borrarProductoProyecto(ProyectoProducto productoProyecto) {
    	productosProyecto.remove(productoProyecto);
    }*/
	
	// PRODUCTOS
	private void cargarListaProductosConvocatoria() {
		this.productoTipoNivel1 = new ArrayList();
		this.productoTipoNivel2 = new ArrayList();
		this.productoTipoNivel3 = new ArrayList();
		
		this.listaproductosTipo = servicioGeneral.obtenerListaObjetos("ProductoTipo");
		ProductoTipo producto = new ProductoTipo();
		producto.setId(ProductoTipo.RAIZ);
		
		this.listaProductosItem = obtenerHijos(producto.getId().toString(),(List) new ArrayList(), true);
		this.productoTipoNivel1.addAll(obtenerHijos("3", (List) new ArrayList(), false));
		ProductoTipo p;
		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			p = (ProductoTipo) this.productoTipoNivel1.get(i);
			p.setTieneHijos(false);
			p.setMostrarHijos(false);
			p.setHijos(obtenerHijos(p.getId().trim(), new ArrayList(), false));
			if (p.getHijos().size() > 0) {
				p.setTieneHijos(true);
				for (Iterator it = p.getHijos().iterator(); it.hasNext();) {
					ProductoTipo hijo = (ProductoTipo) it.next();
					hijo.setTieneHijos(false);
					hijo.setMostrarHijos(false);
					hijo.setHijos(obtenerHijos(hijo.getId().trim(),	new ArrayList(), false));
					if (hijo.getHijos().size() > 0) {
						hijo.setTieneHijos(true);
					}
				}
			}
		}
		p = new ProductoTipo();
		p.setId("-1");
		p.setNombre("--Seleccione--");
		this.productoTipoNivel2.add(p);
		this.productoTipoNivel2.addAll(obtenerHijos(((ProductoTipo) this.productoTipoNivel1.get(0)).getId(),(List) new ArrayList(), false));
		for (int i = 0; i < this.productoTipoNivel2.size(); i++) {
			p = (ProductoTipo) this.productoTipoNivel2.get(i);
		}
	}
	
	private List obtenerHijos(String idPadre, List listaProductos, boolean multiNivel) {
		List listaHijos = servicioGeneral.obtenerListaProductoHijo(idPadre);
		if (multiNivel) {
			if (listaHijos.size() != 0) {
				for (int i = 0; i < listaHijos.size(); i++) {
					listaProductos.add((ProductoTipo) listaHijos.get(i));
					listaProductos = obtenerHijos(((ProductoTipo) listaHijos.get(i)).getId(), listaProductos, true);
				}
			}
		} else {
			listaProductos = listaHijos;
		}
		return listaProductos;
	}

	private void mostrarHijosPorNivel(ProductoTipo productoTipo) {

		String idPadreActual = "";
		String idPadreSiguiente = "";

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo p = (ProductoTipo) this.productoTipoNivel1.get(i);
			if (p.getId().equalsIgnoreCase(productoTipo.getId())) {
				p.setMostrarHijos(true);
			} else {
				p.setMostrarHijos(false);
				if (p.getHijos().size() > 0) {
					for (int j = 0; j < p.getHijos().size(); j++) {
						ProductoTipo hijo = (ProductoTipo) p.getHijos().get(j);
						if (hijo.getId().equalsIgnoreCase(productoTipo.getId())) {
							productoTipo.setTieneHijos(true);
							p.setMostrarHijos(true);
							hijo.setMostrarHijos(true);
							idPadreSiguiente = hijo.getId();
						} else {
							if (hijo.isMostrarHijos()) {
								idPadreActual = hijo.getId();
								hijo.setMostrarHijos(false);
							}
						}
					}
				}
			}
		}
		listaTemporalProductoSeleccionados.put(idPadreActual, this.listaProductoSeleccionados);
		this.listaProductoSeleccionados = (String[]) listaTemporalProductoSeleccionados.get(idPadreSiguiente);
	}

	public void obtenerProductoHijo(ValueChangeEvent event) {
		String nivel = event.getComponent().getId();
		String idPadre = event.getNewValue().toString();
		int nivelSelect = Integer.parseInt(nivel.substring(nivel.length() - 1));
		List hijos = new ArrayList();
		ProductoTipo p = new ProductoTipo();
		p.setId("-1");
		p.setNombre("--Seleccione--");
		hijos.add(p);
		hijos.addAll(obtenerHijos(idPadre, (List) new ArrayList(), false));

		switch (nivelSelect) {
		case 1:
			this.productoTipoNivel2 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[1] = false;
			} else {
				this.readOnlySelect[1] = true;
			}
			if (idPadre.equals("-1")) {
				this.productoActual.getPadre().setId("0");
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		case 2:
			this.productoTipoNivel3 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[2] = false;
			} else {
				this.readOnlySelect[2] = true;
			}
			if (idPadre.equals("-1")) {
				if (seleccionSelect[0] != null) {
					this.productoActual.getPadre().setId(seleccionSelect[0]);
				}
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		}
	}

	public void agregarProducto() {
		// se crea un nuevo producto
		for (int i = 0; i < this.listaproductosTipo.size(); i++) {
			if (((ProductoTipo) this.listaproductosTipo.get(i)).getId().equals(
					this.productoActual.getPadre().getId())) {
				ProductoTipo productoPadre = (ProductoTipo) this.listaproductosTipo
						.get(i);
				productoActual.setPadre(productoPadre);
				if (!productoPadre.getNivel().equalsIgnoreCase("0")) {
					productoActual.setNivel(Integer.toString((Integer
							.parseInt(((ProductoTipo) this.listaproductosTipo
									.get(i)).getNivel()) + 1)));
					break;
				} else {
					productoActual.setNivel("0");
					break;
				}
			}
		}
		servicioGeneral.insertarObjeto(this.productoActual);
		// reseteo producto actual
		this.productoActual.setNombre("");
		this.productoActual.setDescripcion("");
		// cargo de nuevo las listas de productos
		cargarListaProductosConvocatoria();
	}

	public void cargarProductosSeleccionados() {
		List listaProductoConvo = new ArrayList();
//TODO		listaProductoConvo.addAll(this.convocatoriaActual.getProductos());

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo tp = (ProductoTipo) this.productoTipoNivel1.get(i);
			List listaProductoArray = new ArrayList();

			for (int j = 0; j < tp.getHijos().size(); j++) {
				ProductoTipo hijo = (ProductoTipo) tp.getHijos().get(j);

				for (int k = 0; k < hijo.getHijos().size(); k++) {
					ProductoTipo nieto = (ProductoTipo) hijo.getHijos().get(k);

					for (Iterator it = listaProductoConvo.iterator(); it
							.hasNext();) {
						ProductoTipo tipoProducto = (ProductoTipo) it.next();
						if (nieto.getId().equals(tipoProducto.getId())) {
							listaProductoArray.add(nieto);
						}
					}
				}

				this.listaProductoSeleccionados = new String[listaProductoArray
						.size()];
				if (listaProductoArray.size() > 0) {
					for (int l = 0; l < listaProductoArray.size(); l++) {
						ProductoTipo aux = (ProductoTipo) listaProductoArray
								.get(l);
						this.listaProductoSeleccionados[l] = aux.getId();
					}
					listaTemporalProductoSeleccionados.put(hijo.getId(),
							listaProductoSeleccionados);
				}
			}
		}
	}

	private boolean validarProductos() {
		this.mostrarHijosPorNivel(new ProductoTipo());
		List productos = new ArrayList();

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo p = (ProductoTipo) this.productoTipoNivel1.get(i);

			for (int j = 0; j < p.getHijos().size(); j++) {
				ProductoTipo hijo = (ProductoTipo) p.getHijos().get(j);
				String[] productosSeleccionados = (String[]) listaTemporalProductoSeleccionados
						.get(hijo.getId());

				if (productosSeleccionados != null) {
					for (Iterator it = hijo.getHijos().iterator(); it.hasNext();) {
						ProductoTipo productoTipo = (ProductoTipo) it.next();
						for (int k = 0; k < productosSeleccionados.length; k++) {
							if (productoTipo.getId().equalsIgnoreCase(
									productosSeleccionados[k])) {
								productos.add(p);
								productos.add(hijo);
								productos.add(productoTipo);
							}
						}
					}
				}
			}
		}
	//TODO	convocatoriaActual.setProductos(new HashSet(productos));

		if (productos.size() > 0) {
			productos = null;
			this.avisoProductos = "";
			return true;
		} else {
			productos = null;
			this.avisoProductos = "No han sido asociados productos a la convocatoria.";
			return false;
		}
	}
		
	// GET & SET

	public String getActivdadECP() {
		return activdadECP;
	}

	public void setActivdadECP(String activdadECP) {
		this.activdadECP = activdadECP;
	}

	public String getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(String areaTematica) {
		this.areaTematica = areaTematica;
	}

	public String getEjeTematico() {
		return ejeTematico;
	}

	public void setEjeTematico(String ejeTematico) {
		this.ejeTematico = ejeTematico;
	}

	public String getCodAreaTematica() {
		return codAreaTematica;
	}

	public void setCodAreaTematica(String codAreaTematica) {
		this.codAreaTematica = codAreaTematica;
	}

	public String getGrupoBusqueda() {
		return grupoBusqueda;
	}

	public void setGrupoBusqueda(String grupoBusqueda) {
		this.grupoBusqueda = grupoBusqueda;
	}
	
	public String getPlanGlobal() {
		return planGlobal;
	}

	public void setPlanGlobal(String planGlobal) {
		this.planGlobal = planGlobal;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Dependencia getFacultad() {
		return facultad;
	}

	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public List<SelectItem> getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(List<SelectItem> sedeItem) {
		this.sedeItem = sedeItem;
	}

	public List<SelectItem> getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(List<SelectItem> facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List<SelectItem> getDepartamentoItem() {
		return departamentoItem;
	}

	public void setDepartamentoItem(List<SelectItem> departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	public boolean isMostrarFacultad() {
		return mostrarFacultad;
	}

	public void setMostrarFacultad(boolean mostrarFacultad) {
		this.mostrarFacultad = mostrarFacultad;
	}

	public boolean isMostrarDepartamento() {
		return mostrarDepartamento;
	}

	public void setMostrarDepartamento(boolean mostrarDepartamento) {
		this.mostrarDepartamento = mostrarDepartamento;
	}

	public String getAreaResponsable() {
		return areaResponsable;
	}

	public void setAreaResponsable(String areaResponsable) {
		this.areaResponsable = areaResponsable;
	}

	public List getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public String getNombreProd() {
		return nombreProd;
	}

	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}

	public String getTpProd() {
		return tpProd;
	}

	public void setTpProd(String tpProd) {
		this.tpProd = tpProd;
	}

	public String getTipoProdItem() {
		return tipoProdItem;
	}

	public void setTipoProdItem(String tipoProdItem) {
		this.tipoProdItem = tipoProdItem;
	}

	public String getCantidadProd() {
		return cantidadProd;
	}

	public void setCantidadProd(String cantidadProd) {
		this.cantidadProd = cantidadProd;
	}

	public String getFecEntregaProd() {
		return fecEntregaProd;
	}

	public void setFecEntregaProd(String fecEntregaProd) {
		this.fecEntregaProd = fecEntregaProd;
	}

	public List getListaProds() {
		return listaProds;
	}

	public void setListaProds(List listaProds) {
		this.listaProds = listaProds;
	}

	public String getAntecedente() {
		return antecedente;
	}

	public void setAntecedente(String antecedente) {
		this.antecedente = antecedente;
	}

	public List getListaAntecedentes() {
		return listaAntecedentes;
	}

	public void setListaAntecedentes(List listaAntecedentes) {
		this.listaAntecedentes = listaAntecedentes;
	}

	public boolean isMostrarPagListaDpn() {
		return mostrarPagListaDpn;
	}

	public void setMostrarPagListaDpn(boolean mostrarPagListaDpn) {
		this.mostrarPagListaDpn = mostrarPagListaDpn;
	}

	public boolean isMostrarPagListaAnt() {
		return mostrarPagListaAnt;
	}

	public void setMostrarPagListaAnt(boolean mostrarPagListaAnt) {
		this.mostrarPagListaAnt = mostrarPagListaAnt;
	}

	public String getTpProd_1() {
		return tpProd_1;
	}

	public void setTpProd_1(String tpProd_1) {
		this.tpProd_1 = tpProd_1;
	}

	public String getTpProd_2() {
		return tpProd_2;
	}

	public void setTpProd_2(String tpProd_2) {
		this.tpProd_2 = tpProd_2;
	}

	public String getTpProd_3() {
		return tpProd_3;
	}

	public void setTpProd_3(String tpProd_3) {
		this.tpProd_3 = tpProd_3;
	}

	public List getProductoTipoNivel1() {
		return productoTipoNivel1;
	}

	public void setProductoTipoNivel1(List productoTipoNivel1) {
		this.productoTipoNivel1 = productoTipoNivel1;
	}

	public List getProductoTipoNivel2() {
		return productoTipoNivel2;
	}

	public void setProductoTipoNivel2(List productoTipoNivel2) {
		this.productoTipoNivel2 = productoTipoNivel2;
	}

	public List getProductoTipoNivel3() {
		return productoTipoNivel3;
	}

	public void setProductoTipoNivel3(List productoTipoNivel3) {
		this.productoTipoNivel3 = productoTipoNivel3;
	}
	
	
	
}