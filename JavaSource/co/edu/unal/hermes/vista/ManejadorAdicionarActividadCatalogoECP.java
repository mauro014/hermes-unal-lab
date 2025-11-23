package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.AgendaConocimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;

public class ManejadorAdicionarActividadCatalogoECP extends ManejadorBase{
	
	//ATRIBUTOS ACTIVIDAD ECP
    private Long id;
    
    private String persona;
    private Dependencia dependencia;
    private String nombre;
    private String objeto;
    
    private String modalidad;
    private String tipoEvento;
    
    private Long tieneCertificadoCalidad;
    private Long copiaCertificadoCalidad;
    
    private String poblacionObjeto;
    private Long esOfertaPermanente;
    
    private AgendaConocimiento ejeTematico;
    private AgendaConocimiento areaGeneral;
    private AgendaConocimiento areaEspecifica;
    
    private Long anioPrimeraVez;
    private Long mesPrimeraVez;
    
    private Long registroCompleto;
    private Date fechaRegistro;       
    
    // LISTAS
    private List<SelectItem> subModalidadECPItem;
    private List<SelectItem> ejeTematicoItem;
    private List<SelectItem> mesItem;
    
    // GET & SET ATRIBUTOS
    public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	public Dependencia getDependencia() {
		return dependencia;
	}
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public Long getTieneCertificadoCalidad() {
		return tieneCertificadoCalidad;
	}
	public void setTieneCertificadoCalidad(Long tieneCertificadoCalidad) {
		this.tieneCertificadoCalidad = tieneCertificadoCalidad;
	}
	public Long getCopiaCertificadoCalidad() {
		return copiaCertificadoCalidad;
	}
	public void setCopiaCertificadoCalidad(Long copiaCertificadoCalidad) {
		this.copiaCertificadoCalidad = copiaCertificadoCalidad;
	}
	public String getPoblacionObjeto() {
		return poblacionObjeto;
	}
	public void setPoblacionObjeto(String poblacionObjeto) {
		this.poblacionObjeto = poblacionObjeto;
	}
	public Long getEsOfertaPermanente() {
		return esOfertaPermanente;
	}
	public void setEsOfertaPermanente(Long esOfertaPermanente) {
		this.esOfertaPermanente = esOfertaPermanente;
	}
	public AgendaConocimiento getEjeTematico() {
		return ejeTematico;
	}
	public void setEjeTematico(AgendaConocimiento ejeTematico) {
		this.ejeTematico = ejeTematico;
	}
	public Long getAnioPrimeraVez() {
		return anioPrimeraVez;
	}
	public void setAnioPrimeraVez(Long anioPrimeraVez) {
		this.anioPrimeraVez = anioPrimeraVez;
	}
	public Long getMesPrimeraVez() {
		return mesPrimeraVez;
	}
	public void setMesPrimeraVez(Long mesPrimeraVez) {
		this.mesPrimeraVez = mesPrimeraVez;
	}
	public Long getRegistroCompleto() {
		return registroCompleto;
	}
	public void setRegistroCompleto(Long registroCompleto) {
		this.registroCompleto = registroCompleto;
	}
	public List<SelectItem> getSubModalidadECPItem() {
		return subModalidadECPItem;
	}
	public void setSubModalidadECPItem(List<SelectItem> subModalidadECPItem) {
		this.subModalidadECPItem = subModalidadECPItem;
	}
	public List<SelectItem> getEjeTematicoItem() {
		return ejeTematicoItem;
	}
	public void setEjeTematicoItem(List<SelectItem> ejeTematicoItem) {
		this.ejeTematicoItem = ejeTematicoItem;
	}
	public List<SelectItem> getMesItem() {
		return mesItem;
	}
	public void setMesItem(List<SelectItem> mesItem) {
		this.mesItem = mesItem;
	}
    

	
	
	// CONSTRUCTOR
    public ManejadorAdicionarActividadCatalogoECP (){
    	cargarEjeTematico();
    	cargarListaSubModalidadECP();
    	cargarMesItem();
    	dependencia = new Dependencia();
    }

	// METODOS        
    public void cargarListaSubModalidadECP(){
	    subModalidadECPItem = new ArrayList<SelectItem>();	   
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 26";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	subModalidadECPItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }    
    }
    
    public void cargarEjeTematico(){
	    ejeTematicoItem = new ArrayList<SelectItem>();	   
	    
	    String consulta = "select ac from AgendaConocimiento ac where ac.padre = 0 order by ac.nombre";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    for (int i=0; i<lista.size();i++){
	    	AgendaConocimiento agenda = (AgendaConocimiento) lista.get(i);
	    	ejeTematicoItem.add(new SelectItem(agenda.getId(), agenda.getNombre()));
	    }    	
    }
    public void cargarMesItem(){
    	mesItem = new ArrayList<SelectItem>();	   
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 38 order by to_number(dd.identificador.tipo)";
	    List lista = servicioGeneral.obtenerObjetos(consulta);	    
	    for (int i=0; i<lista.size();i++){
	    	DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    	mesItem.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	    }
    }
    
}
