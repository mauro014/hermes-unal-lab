package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;

public class ManejadorAdministrarEntidad extends ManejadorBase{
	
    protected String descripcion_busqueda;
    protected String nit_busqueda;
    protected String naturaleza_busqueda;
    protected String sector_busqueda;
    protected String nvlterritorial_busqueda;
    protected String id;
    protected String descripcion;
    protected String internaExterna;
    protected String id_sige;
    protected String nit;
    protected String direccion;
    protected String telefono;
    protected String fax;
    protected String pais;
    protected String departamento;
    protected String ciudad;
    protected String pagweb;
    protected String naturaleza;
    protected String sector;
    protected String nvlterritorial;
    protected String estado;
    protected String est_entidad;
    protected String reg_completo;
    protected Date fec_registro;
    protected Date fec_solicitud;
    protected String est_solicitud;
    protected Date fec_respuesta;
    protected String observaciones;	
	protected List<SelectItem> estadoItem;
	protected List<SelectItem> naturalezaItem;
	protected List<SelectItem> sectorItem;
	protected List<SelectItem> nvlterritorialItem;
	protected FuenteFinanciacion entidad;
	protected List<FuenteFinanciacion> listaEntidades;
	FuenteFinanciacion entidadSeleccionada;
	protected boolean verEditarEntidades;
	protected SelectItem[] paisItem;
	protected List<SelectItem> departamentoItem;
	protected List<SelectItem> ciudadItem;
	protected boolean guardado;
	protected List<Pais> listaPaises;
	protected Pais pais_reg;
	protected Departamento depto_reg;
	protected Ciudad ciud_reg;
	protected boolean tamlista;
	protected boolean verDeptCiudConsulta;
	protected boolean verDeptCiudEditar;
	
	////////////////////////	GET & SET	/////////////////////////////////
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getInternaExterna() {
		return internaExterna;
	}
	public void setInternaExterna(String internaExterna) {
		this.internaExterna = internaExterna;
	}
	public String getId_sige() {
		return id_sige;
	}
	public void setId_sige(String id_sige) {
		this.id_sige = id_sige;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPagweb() {
		return pagweb;
	}
	public void setPagweb(String pagweb) {
		this.pagweb = pagweb;
	}
	public String getNaturaleza() {
		return naturaleza;
	}
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getNvlterritorial() {
		return nvlterritorial;
	}
	public void setNvlterritorial(String nvlterritorial) {
		this.nvlterritorial = nvlterritorial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEst_entidad() {
		return est_entidad;
	}
	public void setEst_entidad(String est_entidad) {
		this.est_entidad = est_entidad;
	}
	public String getReg_completo() {
		return reg_completo;
	}
	public void setReg_completo(String reg_completo) {
		this.reg_completo = reg_completo;
	}
	public String getEst_solicitud() {
		return est_solicitud;
	}
	public void setEst_solicitud(String est_solicitud) {
		this.est_solicitud = est_solicitud;
	}
	public Date getFec_registro() {
		return fec_registro;
	}
	public void setFec_registro(Date fec_registro) {
		this.fec_registro = fec_registro;
	}
	public Date getFec_solicitud() {
		return fec_solicitud;
	}
	public void setFec_solicitud(Date fec_solicitud) {
		this.fec_solicitud = fec_solicitud;
	}
	public Date getFec_respuesta() {
		return fec_respuesta;
	}
	public void setFec_respuesta(Date fec_respuesta) {
		this.fec_respuesta = fec_respuesta;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public List<SelectItem> getEstadoItem() {
		return estadoItem;
	}
	public void setEstadoItem(List<SelectItem> estadoItem) {
		this.estadoItem = estadoItem;
	}
	public List<SelectItem> getNaturalezaItem() {
		return naturalezaItem;
	}
	public void setNaturalezaItem(List<SelectItem> naturalezaItem) {
		this.naturalezaItem = naturalezaItem;
	}
	public List<SelectItem> getSectorItem() {
		return sectorItem;
	}
	public void setSectorItem(List<SelectItem> sectorItem) {
		this.sectorItem = sectorItem;
	}
	public List<SelectItem> getNvlterritorialItem() {
		return nvlterritorialItem;
	}
	public void setNvlterritorialItem(List<SelectItem> nvlterritorialItem) {
		this.nvlterritorialItem = nvlterritorialItem;
	}
	public FuenteFinanciacion getEntidad() {
		return entidad;
	}
	public void setEntidad(FuenteFinanciacion entidad) {
		this.entidad = entidad;
	}
	public List<FuenteFinanciacion> getListaEntidades() {
		return listaEntidades;
	}
	public void setListaEntidades(List<FuenteFinanciacion> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}
	public FuenteFinanciacion getEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	public void setEntidadSeleccionada(FuenteFinanciacion entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}	
	public String getDescripcion_busqueda() {
		return descripcion_busqueda;
	}
	public void setDescripcion_busqueda(String descripcion_busqueda) {
		this.descripcion_busqueda = descripcion_busqueda;
	}
	public String getNit_busqueda() {
		return nit_busqueda;
	}
	public void setNit_busqueda(String nit_busqueda) {
		this.nit_busqueda = nit_busqueda;
	}
	public String getNaturaleza_busqueda() {
		return naturaleza_busqueda;
	}
	public void setNaturaleza_busqueda(String naturaleza_busqueda) {
		this.naturaleza_busqueda = naturaleza_busqueda;
	}
	public String getSector_busqueda() {
		return sector_busqueda;
	}
	public void setSector_busqueda(String sector_busqueda) {
		this.sector_busqueda = sector_busqueda;
	}
	public String getNvlterritorial_busqueda() {
		return nvlterritorial_busqueda;
	}
	public void setNvlterritorial_busqueda(String nvlterritorial_busqueda) {
		this.nvlterritorial_busqueda = nvlterritorial_busqueda;
	}	
	public boolean isVerEditarEntidades() {
		return verEditarEntidades;
	}
	public void setVerEditarEntidades(boolean verEditarEntidades) {
		this.verEditarEntidades = verEditarEntidades;
	}		
	public SelectItem[] getPaisItem() {
		return paisItem;
	}
	public void setPaisItem(SelectItem[] paisItem) {
		this.paisItem = paisItem;
	}
	public List<SelectItem> getDepartamentoItem() {
		return departamentoItem;
	}
	public void setDepartamentoItem(List<SelectItem> departamentoItem) {
		this.departamentoItem = departamentoItem;
	}
	public List<SelectItem> getCiudadItem() {
		return ciudadItem;
	}
	public void setCiudadItem(List<SelectItem> ciudadItem) {
		this.ciudadItem = ciudadItem;
	}
	public boolean isGuardado() {
		return guardado;
	}
	public void setMostrarDeptoCiud(boolean guardado) {
		this.guardado = guardado;
	}
	public List<Pais> getListaPaises() {
		return listaPaises;
	}
	public void setListaPaises(List<Pais> listaPaises) {
		this.listaPaises = listaPaises;
	}	
	public boolean isTamlista() {
		return tamlista;
	}
	public void setTamlista(boolean tamlista) {
		this.tamlista = tamlista;
	}
	public boolean isVerDeptCiudConsulta() {
		return verDeptCiudConsulta;
	}
	public void setVerDeptCiudConsulta(boolean verDeptCiudConsulta) {
		this.verDeptCiudConsulta = verDeptCiudConsulta;
	}
	public boolean isVerDeptCiudEditar() {
		return verDeptCiudEditar;
	}
	public void setVerDeptCiudEditar(boolean verDeptCiudEditar) {
		this.verDeptCiudEditar = verDeptCiudEditar;
	}

	////////////////////////	CONSTRUCTOR	/////////////////////////////////
	

	public ManejadorAdministrarEntidad(){
		naturalezaItem = new ArrayList<SelectItem>();
		entidad = new FuenteFinanciacion();
		sectorItem = new ArrayList<SelectItem>();
		listaEntidades = new ArrayList<FuenteFinanciacion>();		
		cargarNaturaleza();		
		cargarNvlTerritorial();
		cargarSector();
		cargarDatosEntidad();
		cargarPaises();
		cargarEstado();
	}	
	
	////////////////////////	METODOS	/////////////////////////////////
	
	public String volver(){
		limpiarCampos();		
		return "administrarEntidades";
	}
	private void cargarPaises() {				
		listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		paisItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			paisItem[i] = new SelectItem(p.getId(), p.getNombre());
		}		
	}	
	public void cargarDepto(){			    
		departamentoItem = new ArrayList<SelectItem>();
	    
	   // String idEstado = (String)idPais.getNewValue();
		if (pais.equals("CO")) {
		    String consulta = "select dd from Departamento dd where dd.id like '" + pais + "%' ";
		    List lista = servicioGeneral.obtenerObjetos(consulta);
		    
		    for (int i=0; i<lista.size();i++){
		    	Departamento depto = (Departamento) lista.get(i);	    	
		    	departamentoItem.add(new SelectItem(depto.getId(), depto.getNombre()));
		    }
		}
	}	
	public void cargarCiudad(){
	    ciudadItem = new ArrayList<SelectItem>();
	    
	    //String idEstado = (String)idDepto.getNewValue();
	    if (pais.equals("CO")) {
		    String consulta = "select cc from Ciudad cc where cc.departamento like '" + departamento + "%' ";
		    List lista = servicioGeneral.obtenerObjetos(consulta);
		    
		    for (int i=0; i<lista.size();i++){
		    	Ciudad ciudad = (Ciudad) lista.get(i);	    	
		    	ciudadItem.add(new SelectItem(ciudad.getId(), ciudad.getNombre()));
		    }
	    }
	}	
	public void limpiarCampos(){
		this.id = "";
		this.descripcion = "";
		this.internaExterna = "";
		this.id_sige = "";
		this.nit = "";
		this.direccion = "";
		this.telefono = "";
		this.fax = "";
		this.pais = "";
		this.departamento = "";
		this.ciudad = "";
		this.pagweb = "";
		this.naturaleza = "";
		this.sector = "";
		this.nvlterritorial = "";
		this.estado = "";
		this.est_entidad = "";
		this.reg_completo = "";
		this.fec_registro = null;
		this.fec_solicitud = null;
		this.est_solicitud = null;
		this.fec_respuesta = null;
		this.observaciones = "";
		verDeptCiudConsulta = false;
		verDeptCiudEditar = false;
		departamentoItem = null;
		ciudadItem = null;
	}
	public void cargarEstado(){
		estadoItem = new ArrayList<SelectItem>();
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 24";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	estadoItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }	    	    
	}
	public void cargarNaturaleza(){
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 22 order by dd.identificador.tipo";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    
	    naturalezaItem.add(new SelectItem("1", "Seleccione uno"));
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	naturalezaItem.add(new SelectItem(dominio.getIdentificador().getTipo(),dominio.getDescripcion()));
	    }	    
	}	
	public void cargarSector(){
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 23";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    
	    sectorItem.add(new SelectItem("1", "Seleccione uno"));
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	sectorItem.add(new SelectItem(dominio.getIdentificador().getTipo(),dominio.getDescripcion()));
	    }	    	    
	}	
	public void cargarNvlTerritorial(){
	    
		nvlterritorialItem = new ArrayList<SelectItem>();		
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 25";
	    List lista = servicioGeneral.obtenerObjetos(consulta);
	    
	    nvlterritorialItem.add(new SelectItem("1", "Seleccione uno"));
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	nvlterritorialItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }
	}	
	public void eliminarEntidad(){
		FuenteFinanciacion entidadConsulta = new FuenteFinanciacion();		
		entidadConsulta = entidadSeleccionada;
		servicioGeneral.eliminarObjeto(entidadConsulta);
		listaEntidades.remove(entidadSeleccionada);
	}
	public void verDescNit(){
		FuenteFinanciacion entidadConsulta = new FuenteFinanciacion();		
		entidadConsulta = entidadSeleccionada;
		this.entidad.setDescripcion(this.descripcion);
		this.entidad.setNit(this.nit);
		this.descripcion = entidadSeleccionada.getDescripcion();
		this.nit = entidadSeleccionada.getNit();
	}
	public void cargarDatosEntidad(){	    		
		listaEntidades = new ArrayList<FuenteFinanciacion>();
	    String consulta = "select ff from FuenteFinanciacion ff where  ff.est_entidad is not null and ff.est_solicitud like 'EST_EN_AC' order by ff.descripcion";
	    List lista = servicioGeneral.obtenerObjetos(consulta);
	    for (int i=0; i<lista.size();i++){ 
        	FuenteFinanciacion entidad = (FuenteFinanciacion) lista.get(i);
        	listaEntidades.add(entidad);
        }
	    if(listaEntidades.size()>0)
	    	tamlista = true;
	    else
	    	tamlista = false;
	}	
	public void buscarEntidad(){		
		
		this.listaEntidades = null;		
		listaEntidades = new ArrayList<FuenteFinanciacion>();
		
		String consulta, desc="", natu="", sect="", nvlt="", nit="";
		
		if (this.naturaleza_busqueda.equals("1"))
			this.naturaleza_busqueda = "";
		if (this.nvlterritorial_busqueda.equals("1"))
			this.nvlterritorial_busqueda="";				
		if (this.sector_busqueda.equals("1"))
			this.sector_busqueda="";		
		
		if (this.descripcion_busqueda.length() > 0 )
			desc = " and ff.descripcion like upper('" + this.descripcion_busqueda.trim() + "%')";
		if (this.nit_busqueda.length() > 0)
			nit = " and ff.nit like '"+this.nit_busqueda.trim() +"%'";
		if (this.naturaleza_busqueda.length() > 0)
			natu = " and ff.naturaleza like '" + this.naturaleza_busqueda + "%'";
		if (this.sector_busqueda.length() > 0)
			sect = " and ff.sector like '" + this.sector_busqueda + "%'";
		if (this.nvlterritorial_busqueda.length() > 0)
			nvlt = " and ff.nvlterritorial like '"+ this.nvlterritorial_busqueda +"%'";
		
		consulta = "select ff from FuenteFinanciacion ff where  ff.est_entidad is not null and ff.est_solicitud like 'EST_EN_AC' " + desc + nit + natu + sect + nvlt + " order by ff.descripcion";
		List lista = servicioGeneral.obtenerObjetos(consulta);			
	    for (int i=0; i<lista.size();i++){ 
        	FuenteFinanciacion entidad = (FuenteFinanciacion) lista.get(i);
        	this.listaEntidades.add(entidad);
        }
	    if(listaEntidades.size()>0)
	    	tamlista = true;
	    else
	    	tamlista = false;

	}
	public void buscarValoresListas(){				
		
		String natu, sect, nvlt, esta;						
		
		natu = "select dd.descripcion from DominioDetalle dd where  dd.identificador.tipo like '%"+this.entidadSeleccionada.getNaturaleza()+"%'";
		List lista_natu = servicioGeneral.obtenerObjetos(natu);			
	    this.naturaleza = (String) lista_natu.get(0);
	    
	    sect = "select dd.descripcion from DominioDetalle dd where  dd.identificador.tipo like '%"+this.entidadSeleccionada.getSector()+"%'";
		List lista_sect = servicioGeneral.obtenerObjetos(sect);			
	    this.sector = (String) lista_sect.get(0);
	    
	    if(this.entidadSeleccionada.getNaturaleza().equals("NAT_PUBLIC")){
		    nvlt = "select dd.descripcion from DominioDetalle dd where  dd.identificador.tipo like '%"+this.entidadSeleccionada.getNvlterritorial()+"%'";
			List lista_nvlt = servicioGeneral.obtenerObjetos(nvlt);			
		    this.nvlterritorial = (String) lista_nvlt.get(0);
        }
	    else
	    	this.nvlterritorial = "";
		    
	    esta = "select dd.descripcion from DominioDetalle dd where  dd.identificador.tipo like '%"+this.entidadSeleccionada.getEst_entidad()+"%'";
		List lista_esta = servicioGeneral.obtenerObjetos(esta);			
	    this.estado = (String) lista_esta.get(0);
	    
	    
	    
	}	
	public String consultarEntidad(){
		verDeptCiudConsulta = false;
		verEditarEntidades = false;
		FuenteFinanciacion entidadConsulta = new FuenteFinanciacion();
		entidadConsulta = entidadSeleccionada;
		buscarValoresListas();
		this.id = entidadConsulta.getId();
		this.descripcion = entidadConsulta.getDescripcion();
		this.internaExterna = entidadConsulta.getInternaExterna();
		this.id_sige = entidadConsulta.getId_sige();
		this.nit = entidadConsulta.getNit();
		this.direccion = entidadConsulta.getDireccion();
		this.telefono = entidadConsulta.getTelefono();
		this.fax = entidadConsulta.getFax();
		if(entidadConsulta.getPais().getId().equals("CO") && verEditarEntidades == false)
			verDeptCiudConsulta = true;		
		this.pais = entidadConsulta.getPais().getNombre();
		if (entidadConsulta.getDepartamento() == null)
			this.departamento = "";
		else
			this.departamento = entidadConsulta.getDepartamento().getNombre();
		if (entidadConsulta.getCiudad() == null)
			this.ciudad = "";
		else
			this.ciudad = entidadConsulta.getCiudad().getNombre();
		this.pagweb = entidadConsulta.getPagweb();
		//this.naturaleza = entidadConsulta.getNaturaleza();
		//this.sector = entidadConsulta.getSector();
		//this.nvlterritorial = entidadConsulta.getNvlterritorial();
		//this.estado = entidadConsulta.getEstado();
		//this.est_entidad = entidadConsulta.getEst_entidad();
		this.reg_completo = entidadConsulta.getReg_completo();
		this.fec_registro = entidadConsulta.getFec_registro();
		this.fec_solicitud = entidadConsulta.getFec_solicitud();
		this.fec_respuesta = entidadConsulta.getFec_respuesta();
		this.observaciones = entidadConsulta.getObservaciones();
		
		return "consultarEntidad";
	}	
	public String editarEntidad(){
		verEditarEntidades = true;
		verDeptCiudEditar = false;
		guardado = false;
		if(verEditarEntidades == true)
			verDeptCiudEditar = true;
		this.entidad = entidadSeleccionada;
		this.id = this.entidad.getId();
		this.descripcion = this.entidad.getDescripcion();
		this.internaExterna = this.entidad.getInternaExterna();
		this.nit = this.entidad.getNit();
		this.est_solicitud = this.entidad.getEst_solicitud();
		
		return "consultarEntidad";
	}		
	public void guardar(){		
		this.entidad = entidadSeleccionada;
		
		String consulta_pais, depto, ciud;						
		
		pais_reg = new Pais();
		depto_reg = new Departamento();
		ciud_reg = new Ciudad();
		
		if(!this.pais.equals("00")){
			consulta_pais = "select pp from Pais pp where  pp.id like '%"+this.pais+"%'";
			List lista_pais = servicioGeneral.obtenerObjetos(consulta_pais);			
		    pais_reg = (Pais) lista_pais.get(0);
			this.entidad.setPais(pais_reg);
		}
		else
			this.pais = null;
	    if(this.pais.equals("CO")){
		    depto = "select dd from Departamento dd where  dd.id  like '%"+this.departamento+"%'";
			List lista_depto = servicioGeneral.obtenerObjetos(depto);			
		    depto_reg = (Departamento) lista_depto.get(0);
		    
		    ciud = "select cc from Ciudad cc where  cc.id like '%"+this.ciudad+"%'";
			List lista_ciud = servicioGeneral.obtenerObjetos(ciud);			
		    ciud_reg = (Ciudad) lista_ciud.get(0);
		    
			this.entidad.setDepartamento(depto_reg);
			this.entidad.setCiudad(ciud_reg);
		}	    
		
		this.entidad.setDireccion(this.direccion);
		this.entidad.setTelefono(this.telefono);
		this.entidad.setFax(this.fax);
		this.entidad.setPagweb(this.pagweb);
		if(!this.naturaleza.equals("1"))
			this.entidad.setNaturaleza(this.naturaleza);
		else
			this.entidad.setNaturaleza(null);
		
		if (!this.sector.equals("1"))
			this.entidad.setSector(this.sector);
		else 
			this.entidad.setSector(null);
		
		if(this.entidad.getNaturaleza().equals("NAT_PUBLIC"))
			if(!this.nvlterritorial.equals("1"))
				this.entidad.setNvlterritorial(this.nvlterritorial);
			else
				this.entidad.setNvlterritorial(null);
		else
			this.entidad.setNvlterritorial("");
		//this.entidad.setEstado(this.estado);
		this.entidad.setEst_entidad(this.estado);
		this.entidad.setObservaciones(this.observaciones);
		if(this.descripcion != null && this.nit != null && this.pais  != null && this.estado != null && this.naturaleza != null && this.sector != null){
			servicioGeneral.guardarObjeto(this.entidad);	
			verEditarEntidades = true;
			guardado = true;
			FacesContext.getCurrentInstance().addMessage("msg_upd", new FacesMessage(FacesMessage.SEVERITY_INFO,"La entidad se ha actualizado exitosamente", ""));
			}
		else{
			FacesContext.getCurrentInstance().addMessage("msg_upd", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hay campos obligatorios sin ingresar", ""));
			guardado = false;
			}
	}
}
