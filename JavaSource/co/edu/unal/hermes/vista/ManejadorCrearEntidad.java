package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;

public class ManejadorCrearEntidad extends ManejadorBase{
	
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
	protected List<Pais> listaPaises;
    protected List<SelectItem> listaCiudad;
	protected SelectItem[] paisItem;
	protected List<SelectItem> departamentoItem;
	protected List<SelectItem> ciudadItem;
	protected List<SelectItem> estadoItem;
	protected boolean mostrarDeptoCiud;
	protected List<SelectItem> naturalezaItem;
	protected List<SelectItem> sectorItem;
	protected List<SelectItem> nvlterritorialItem;
	protected boolean mostrarDialog;
	protected FuenteFinanciacion entidad;
	
	protected CorreoPlantilla correoActual = new CorreoPlantilla();
	
	protected Pais pais_reg;
	protected Departamento depto_reg;
	protected Ciudad ciud_reg;
        
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

	public void setId_sige(String idSige) {
		id_sige = idSige;
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

	public void setEst_entidad(String estEntidad) {
		est_entidad = estEntidad;
	}

	public String getReg_completo() {
		return reg_completo;
	}

	public void setReg_completo(String regCompleto) {
		reg_completo = regCompleto;
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

	public String getEst_solicitud() {
		return est_solicitud;
	}

	public void setEst_solicitud(String estSolicitud) {
		est_solicitud = estSolicitud;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<Pais> getListaPaises() {
		return listaPaises;
	}

	public List<SelectItem> getListaCiudad() {
		return listaCiudad;
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

	public List<SelectItem> getEstadoItem() {
		return estadoItem;
	}

	public void setEstadoItem(List<SelectItem> estadoItem) {
		this.estadoItem = estadoItem;
	}

	public boolean isMostrarDeptoCiud() {
		return mostrarDeptoCiud;
	}

	public void setMostrarDeptoCiud(boolean mostrarDeptoCiud) {
		this.mostrarDeptoCiud = mostrarDeptoCiud;
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
	
	public boolean ismostrarDialog() {
		return mostrarDialog;
	}

	public void setCargarNvlT(boolean mostrarDialog) {
		this.mostrarDialog = mostrarDialog;
	}

	public ManejadorCrearEntidad(){		
		listaPaises = new ArrayList<Pais>();
		naturalezaItem = new ArrayList<SelectItem>();
		sectorItem = new ArrayList<SelectItem>();
		cargarPaises();
		cargarEstado();
		cargarNaturaleza();		
		cargarSector();
		cargarNvlTerritorial();
		fechaRegistro();
		entidad = new FuenteFinanciacion();										
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
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	naturalezaItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }	    
	}
	
	public void cargarSector(){
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 23";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	sectorItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }	    	    
	}
	
	public void cargarNvlTerritorial(){
	    
		nvlterritorialItem = new ArrayList<SelectItem>();		
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 25";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	nvlterritorialItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }
	}
	
	public void fechaRegistro(){		
		String dia, mes, annio;
		Calendar sysdate = Calendar.getInstance();
		
		dia = Integer.toString(sysdate.get(Calendar.DATE));
		mes = Integer.toString(sysdate.get(Calendar.MONTH));
		annio = Integer.toString(sysdate.get(Calendar.YEAR));
		
		
		//fec_registro = dia+"/"+mes+"/"+annio;	    
	}
	
	public void buscarValoresListas(){				
		
		String consulta_pais, depto, ciud;						
		
		pais_reg = new Pais();
		depto_reg = new Departamento();
		ciud_reg = new Ciudad();
		
		consulta_pais = "select pp from Pais pp where  pp.id like '%"+this.pais+"%'";
		List lista_pais = servicioGeneral.obtenerObjetos(consulta_pais);			
	    pais_reg = (Pais) lista_pais.get(0);
	    
	    if (pais.equals("CO")) {
		    depto = "select dd from Departamento dd where  dd.id  like '%"+this.departamento+"%'";
			List lista_depto = servicioGeneral.obtenerObjetos(depto);			
		    depto_reg = (Departamento) lista_depto.get(0);
		    
		    ciud = "select cc from Ciudad cc where  cc.id like '%"+this.ciudad+"%'";
			List lista_ciud = servicioGeneral.obtenerObjetos(ciud);			
		    ciud_reg = (Ciudad) lista_ciud.get(0);	    
	    }
	}
	
	public void guardarEntidadSolicitud(){
		try {
			buscarValoresListas();
			
			fec_solicitud = new Date();
			this.entidad.setDescripcion(descripcion.toUpperCase());
			this.entidad.setDireccion(direccion);
			this.entidad.setTelefono(telefono);
			this.entidad.setFax(fax);
			this.entidad.setPais(pais_reg);
			if(pais_reg.getNombre().equals("Colombia") && this.nit.length()>0){
				this.entidad.setDepartamento(depto_reg);
				this.entidad.setCiudad(ciud_reg);					
				this.entidad.setNit(nit);
			}
			else{
				this.entidad.setDepartamento(null);
				this.entidad.setCiudad(null);
				this.entidad.setNit(nit);
			}
			this.entidad.setPagweb(pagweb);
			this.entidad.setNaturaleza(naturaleza);
			this.entidad.setSector(sector);
			if(naturaleza != "NAT_PUBLIC"){
				this.entidad.setNvlterritorial(null);
			}
			else{
				this.entidad.setNvlterritorial(nvlterritorial);
			}			
			this.entidad.setEst_entidad(estado);
			this.entidad.setFec_solicitud(fec_solicitud);
			this.entidad.setObservaciones(observaciones);			
			if(descripcion != null && nit != null && pais  != null && estado != null && naturaleza != null && sector != null){
				reg_completo = "1";
				est_solicitud = "EST_EN_SO";
				this.entidad.setReg_completo(reg_completo);
				this.entidad.setEst_solicitud(est_solicitud);
				this.servicioGeneral.guardarObjeto(this.entidad);
		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La solicitud de creación de la entidad se ha enviado exitosamente", ""));
				mostrarDialog = true;
				// CORREO DNE SOLICITUD
				/*String correoEnvio = "hermes@unal.edu.co";
				int nPlantilla = 149;
				CorreoPlantilla cp = cargarPlantilla(nPlantilla);// 53
				correoActual = cp;
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = personaActual.getEmail();
				correo.adicionarDireccion(dirCorreo);
				correo.adicionarCopiaOculta(correoEnvio);

				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(correoActual.getCuerpo());
				
				// TODO modificar para pruebas
				servicioCorreo.enviarCorreo(correo);*/
			}
			else{
				reg_completo = "0";
				est_solicitud = "EST_EN_BO";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Faltan datos por ingresar", ""));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral.obtenerListaObjetosWhere(CorreoPlantilla.class, "where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = lista.get(0);
		}
		return correoActualAux;
	}
}
