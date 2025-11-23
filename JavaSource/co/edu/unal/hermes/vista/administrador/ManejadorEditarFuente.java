package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.primefaces.model.TreeNode;

import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.SolicitudFuente;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorCambioEstadosAdministrador.
 *
 * @author Liliana Olarte
 * @date 10/01/2016
 */

public class ManejadorEditarFuente extends ManejadorBase {

	
	/*Mensaje*/
	private UIComponent message2;
    
    /** The tipos tiposFuenteFinanciacionItem. */
    public SelectItem[] tiposFuenteFinanciacionItem;
    
    /** The tipos caracterFuenteFinanciacionItem. */
    public SelectItem[] caracterFuenteFinanciacionItem;
	
	private SolicitudFuente solicitud;
	private FuenteFinanciacion fuenteFinanciacion;
	public SelectItem[] tiposNaturalezaFuenteItem;
	public SelectItem[] estadosItem;
	private SelectItem[] estadosFteItem;
	private SelectItem[] listaPaisesItem;
	
	
	/*Constructor*/
	public ManejadorEditarFuente() {
		
		//Estados
		cargarEstados();
		
		Long idSolicitud = (Long) sesion.getAttribute("solicitudEditable");
		if(idSolicitud!=null){
			List lista = servicioGeneral.obtenerObjetoXID("SolicitudFuente", idSolicitud.toString());	
			if(lista.size()>0){
				solicitud = (SolicitudFuente) lista.get(0);
				if(solicitud.getIdFuenteFinanciacion()!=null){
					List listaFte = servicioGeneral.obtenerObjetos("select f from FuenteFinanciacion f where f.id ='" + solicitud.getIdFuenteFinanciacion()  + "'");
					if(listaFte!=null && listaFte.size()>0){
						fuenteFinanciacion = (FuenteFinanciacion) listaFte.get(0);
						solicitud.setIdFuenteFinanciacion(Long.parseLong(fuenteFinanciacion.getId()));
						//System.out.println(solicitud.getNombreSolicitante());
						cargarEstados();
					}
					
				}
				
			}
			
		}	
		
		List<Pais> listaPaises;
		listaPaises = cargarPaises(true);
		listaPaisesItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = (Pais) listaPaises.get(i);
			listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
		}
		
		
	}
	
	/**
	 * Cargar estados.
	 */
	private void cargarEstados() {		
		List listaEstados = new ArrayList<DominioDetalle>();
		listaEstados = servicioGeneral
				.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id ='137' order by dd.descripcion");
		estadosItem = new SelectItem[listaEstados.size()];
		for (int i = 0; i < listaEstados.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaEstados.get(i);
			estadosItem[i] = new SelectItem(dd.getIdentificador()
					.getTipo(), dd.getDescripcion());
			dd = null;
		}
		
		
	}
	
	/*Guardar*/
	public void guardar(){
		try{
			
			if(!fuenteFinanciacion.getInternaExterna().equals("")){
				FuenteFinanciacion fte = new FuenteFinanciacion();
				fte = fuenteFinanciacion; //solicitud.getFuenteFinanciacion();
				servicioGeneral.guardarObjeto(fte);
				
				solicitud.setIdFuenteFinanciacion(Long.parseLong(fte.getId()));
				servicioGeneral.guardarObjeto(solicitud);
				mensajeInfo("La fuente ha sido actualizada con éxito.");
			}else{
				mensajeError("Por favor seleccionar la tipología de la fuente.");
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	//GET SET
	public UIComponent getMessage2() {
		return message2;
	}


	public void setMessage2(UIComponent message2) {
		this.message2 = message2;
	}


	public SolicitudFuente getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(SolicitudFuente solicitud) {
		this.solicitud = solicitud;
	}

	public void setTiposNaturalezaFuenteItem(SelectItem[] tiposNaturalezaFuenteItem) {
		this.tiposNaturalezaFuenteItem = tiposNaturalezaFuenteItem;
	}


	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}


	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	public SelectItem[] getEstadosFteItem() {
		return estadosFteItem;
	}

	public void setEstadosFteItem(SelectItem[] estadosFteItem) {
		this.estadosFteItem = estadosFteItem;
	}

	public int hashCode() {
		return fuenteFinanciacion.hashCode();
	}

	public String toString() {
		return fuenteFinanciacion.toString();
	}

	public void construirTreeGastoFM_CCT() {
		fuenteFinanciacion.construirTreeGastoFM_CCT();
	}

	public void construirTreeGastoFM_Especial() {
		fuenteFinanciacion.construirTreeGastoFM_Especial();
	}

	public void construirTreeGastoFMExt() {
		fuenteFinanciacion.construirTreeGastoFMExt();
	}

	public void construirTreeGastoFMExtInt() {
		fuenteFinanciacion.construirTreeGastoFMExtInt();
	}

	public Object clone() throws CloneNotSupportedException {
		return fuenteFinanciacion.clone();
	}

	public String getDescripcion() {
		return fuenteFinanciacion.getDescripcion();
	}

	public void setDescripcion(String descripcion) {
		fuenteFinanciacion.setDescripcion(descripcion);
	}

	public String getId() {
		return fuenteFinanciacion.getId();
	}

	public void setId(String id) {
		fuenteFinanciacion.setId(id);
	}

	public String getInternaExterna() {
		return fuenteFinanciacion.getInternaExterna();
	}

	public void setInternaExterna(String internaExterna) {
		fuenteFinanciacion.setInternaExterna(internaExterna);
	}

	public String getId_sige() {
		return fuenteFinanciacion.getId_sige();
	}

	public void setId_sige(String idSige) {
		fuenteFinanciacion.setId_sige(idSige);
	}

	public String getNit() {
		return fuenteFinanciacion.getNit();
	}

	public void setNit(String nit) {
		fuenteFinanciacion.setNit(nit);
	}

	public String getDireccion() {
		return fuenteFinanciacion.getDireccion();
	}

	public void setDireccion(String direccion) {
		fuenteFinanciacion.setDireccion(direccion);
	}

	public String getTelefono() {
		return fuenteFinanciacion.getTelefono();
	}

	public void setTelefono(String telefono) {
		fuenteFinanciacion.setTelefono(telefono);
	}

	public String getFax() {
		return fuenteFinanciacion.getFax();
	}

	public void setFax(String fax) {
		fuenteFinanciacion.setFax(fax);
	}

	public Pais getPais() {
		return fuenteFinanciacion.getPais();
	}

	public void setPais(Pais pais) {
		fuenteFinanciacion.setPais(pais);
	}

	public Departamento getDepartamento() {
		return fuenteFinanciacion.getDepartamento();
	}

	public void setDepartamento(Departamento departamento) {
		fuenteFinanciacion.setDepartamento(departamento);
	}

	public Ciudad getCiudad() {
		return fuenteFinanciacion.getCiudad();
	}

	public void setCiudad(Ciudad ciudad) {
		fuenteFinanciacion.setCiudad(ciudad);
	}

	public String getPagweb() {
		return fuenteFinanciacion.getPagweb();
	}

	public void setPagweb(String pagweb) {
		fuenteFinanciacion.setPagweb(pagweb);
	}

	public String getNaturaleza() {
		return fuenteFinanciacion.getNaturaleza();
	}

	public String getNombreNaturaleza() {
		return fuenteFinanciacion.getNombreNaturaleza();
	}

	public void setNaturaleza(String naturaleza) {
		fuenteFinanciacion.setNaturaleza(naturaleza);
	}

	public String getSector() {
		return fuenteFinanciacion.getSector();
	}

	public void setSector(String sector) {
		fuenteFinanciacion.setSector(sector);
	}

	public String getNvlterritorial() {
		return fuenteFinanciacion.getNvlterritorial();
	}

	public void setNvlterritorial(String nvlterritorial) {
		fuenteFinanciacion.setNvlterritorial(nvlterritorial);
	}

	public String getObsRechazo() {
		return fuenteFinanciacion.getObsRechazo();
	}

	public void setObsRechazo(String obsRechazo) {
		fuenteFinanciacion.setObsRechazo(obsRechazo);
	}

	public String getEst_entidad() {
		return fuenteFinanciacion.getEst_entidad();
	}

	public void setEst_entidad(String estEntidad) {
		fuenteFinanciacion.setEst_entidad(estEntidad);
	}

	public String getReg_completo() {
		return fuenteFinanciacion.getReg_completo();
	}

	public void setReg_completo(String regCompleto) {
		fuenteFinanciacion.setReg_completo(regCompleto);
	}

	public Date getFec_registro() {
		return fuenteFinanciacion.getFec_registro();
	}

	public void setFec_registro(Date fec_registro) {
		fuenteFinanciacion.setFec_registro(fec_registro);
	}

	public Date getFec_solicitud() {
		return fuenteFinanciacion.getFec_solicitud();
	}

	public void setFec_solicitud(Date fec_solicitud) {
		fuenteFinanciacion.setFec_solicitud(fec_solicitud);
	}

	public Date getFec_respuesta() {
		return fuenteFinanciacion.getFec_respuesta();
	}

	public void setFec_respuesta(Date fec_respuesta) {
		fuenteFinanciacion.setFec_respuesta(fec_respuesta);
	}

	public String getObservaciones() {
		return fuenteFinanciacion.getObservaciones();
	}

	public void setObservaciones(String observaciones) {
		fuenteFinanciacion.setObservaciones(observaciones);
	}

	public String getEst_solicitud() {
		return fuenteFinanciacion.getEst_solicitud();
	}

	public void setEst_solicitud(String est_solicitud) {
		fuenteFinanciacion.setEst_solicitud(est_solicitud);
	}

	public boolean equals(Object o) {
		return fuenteFinanciacion.equals(o);
	}

	public TreeNode getRoot() {
		return fuenteFinanciacion.getRoot();
	}

	public void setRoot(TreeNode root) {
		fuenteFinanciacion.setRoot(root);
	}

	public String getQuipu() {
		return fuenteFinanciacion.getQuipu();
	}

	public void setQuipu(String quipu) {
		fuenteFinanciacion.setQuipu(quipu);
	}

	public FuenteFinanciacion getFuenteFinanciacion() {
		return fuenteFinanciacion;
	}

	public void setFuenteFinanciacion(FuenteFinanciacion fuenteFinanciacion) {
		this.fuenteFinanciacion = fuenteFinanciacion;
	}

	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
	}

	public SelectItem[] getListaPaisesItem() {
		return listaPaisesItem;
	}

	public void setListaPaisesItem(SelectItem[] listaPaisesItem) {
		this.listaPaisesItem = listaPaisesItem;
	}

	

}
