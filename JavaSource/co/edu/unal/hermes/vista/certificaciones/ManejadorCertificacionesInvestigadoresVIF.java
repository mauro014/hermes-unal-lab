package co.edu.unal.hermes.vista.certificaciones;

import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCertificacionesInvestigadoresVIF extends ManejadorBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3046995852623378048L;
	
	private InvestigadorInterno ii;
	private String nombreCompletoPersona = "";
	private TipoDocumento tipoDocumento;
	private String documento;
	private SelectItem[] tipoDocumentoItem;

	public ManejadorCertificacionesInvestigadoresVIF()
	{
		super();		
		cargarTiposDocumento();
		sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaVIF");
	}
	
	private void cargarTiposDocumento() {
		if (tipoDocumentoItem == null
				|| (tipoDocumentoItem != null && tipoDocumentoItem.length == 0)) {
			List listaTipoDocumento = servicioGeneral
					.obtenerListaObjetos("TipoDocumento");
			tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
			for (int i = 0; i < listaTipoDocumento.size(); i++) {
				TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
				tipoDocumentoItem[i] = new SelectItem(td.getId(),
						td.getNombre());
			}
			tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
		}
	}
	
	public String irGenerarCertificaciones(){
		sesion.removeAttribute("ManejadorCertificaciones");
		IdPersona per = new IdPersona();
		per.setDocumento(documento);
		per.setTipoDocumento(tipoDocumento.getId());
		Persona personaCertVIF = servicioPersona.obtenerPersona(per);
		if(personaCertVIF != null){
			sesion.setAttribute("personaCertVIF", personaCertVIF);
			documento = "";
			tipoDocumento.setId("");
			return "irCertificaciones";
		}else{
			mensajeError("El número de documento y tipo de documento ingresados no corresponden a un usuario registrado en el sistema.");
			return "";
		}
		
	}
	
	public String irInicio()
	{
		sesion.removeAttribute("ManejadorCertificaciones");
		return "misProyectos";
	}

	public InvestigadorInterno getIi() {
		return ii;
	}

	public void setIi(InvestigadorInterno ii) {
		this.ii = ii;
	}

	public String getNombreCompletoPersona() {
		return nombreCompletoPersona;
	}

	public void setNombreCompletoPersona(String nombreCompletoPersona) {
		this.nombreCompletoPersona = nombreCompletoPersona;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}	


}
