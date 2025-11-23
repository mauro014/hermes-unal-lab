/**
 * @author: Mauricio Amaya Ríos
 * @date: 05/02/2015
 */

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class HistoricoEstadoAval implements Serializable  {

	private static final long serialVersionUID = 2215328871659263784L;
	
	private Long id;
	
    private Aval aval;
    private String estadoAval;
    private String avalUab;
    private String avalFacultad;
    private String avalDireccion;
    private String avalVicerrectoria;
    private String justificacion;
    private Date fecha;
    private Persona revisor;
    
    private String avalDRE;
	private String avalRectoria;    
   
	public HistoricoEstadoAval() {
		super();
	}
	
	public HistoricoEstadoAval(Aval aval, String estadoAval,
			String avalFacultad, String avalDireccion,
			String avalVicerrectoria, Persona revisor, String avalUab, String avalDRE, String avalRectoria) {
		super();
		this.aval = aval;
		this.estadoAval = estadoAval;
		this.avalUab = avalUab;
		this.avalFacultad = avalFacultad;
		this.avalDireccion = avalDireccion;
		this.avalVicerrectoria = avalVicerrectoria;
		this.fecha = new Date();
		this.revisor = revisor;
		setAvalDRE(avalDRE);
		setAvalRectoria(avalRectoria);
	}

	public String getNombreEstado(){
		
		String nombreEstado = "";
		
		try{
			if(estadoAval!=null && estadoAval.equals(Aval.FORMULACION)){
				nombreEstado = "Formulación";
			}else if(estadoAval!=null && estadoAval.equals(Aval.BORRADO)){
				nombreEstado = "Solicitud anulada";
			}else if(estadoAval!=null && estadoAval.equals(Aval.ENVIADO)){
				nombreEstado = "Enviado";
			}else if(estadoAval!=null && estadoAval.equals(Aval.ENVIADO_FACULTAD)){
				nombreEstado = "Enviado a Facultad";
			}else if(estadoAval!=null && estadoAval.equals(Aval.ENVIADO_SEDE)){
				nombreEstado = "Enviado a Sede";
			}else if(estadoAval!=null && estadoAval.equals(Aval.DEVUELTO)){
				nombreEstado = "Devuelto para correcciones";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_FACULTAD) && avalFacultad!=null && avalFacultad.equals(Aval.APROBADO) && aval.getTipo()!=null && (aval.getTipo().equals(Aval.TIPO_REGALIAS) && aval.getAviId()<=7478L)){
				nombreEstado = "Enviado";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_FACULTAD) && avalFacultad!=null && avalFacultad.equals(Aval.APROBADO) && aval.getTipo()!=null && (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT) && aval.getDependenciaContrapartida().equals("D") )){
				nombreEstado = "Enviado";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_FACULTAD) && avalFacultad!=null && avalFacultad.equals(Aval.APROBADO) && (aval.getDependencia().getId().startsWith("6") || aval.getDependencia().getId().startsWith("7") || aval.getDependencia().getId().startsWith("8") ) ){
				nombreEstado = "Enviado";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_UAB) && avalUab!=null && avalUab.equals(Aval.APROBADO) && avalFacultad == null && avalDireccion == null){
				nombreEstado = "Aprobado en UAB";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_UAB) && avalUab!=null && avalUab.equals(Aval.APROBADO) && avalFacultad != null && avalFacultad.equals(Aval.APROBADO)){
			    nombreEstado = "Aprobado en UAB - Asignado coordinador en Vicedecanatura";
            }else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_UAB) && avalUab!=null && avalUab.equals(Aval.APROBADO) && avalDireccion != null && avalDireccion.equals(Aval.APROBADO)){
                nombreEstado = "Aprobado en UAB - Asignado coordinador en Dirección";
            }else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_UAB) && avalUab!=null && avalUab.equals(Aval.NEGADO)){
				nombreEstado = "No aprobado en UAB";
			}else if(estadoAval!=null && Aval.REVISADO_FACULTAD.equals(this.estadoAval) && Aval.APROBADO.equals(this.avalFacultad) && aval.getTipo().equals(Aval.TIPO_JORNADA_DOCENTE)
	                && this.avalUab == null){ 
			    nombreEstado = "Aprobado en UAB";
	        }else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_FACULTAD) && avalFacultad!=null && avalFacultad.equals(Aval.APROBADO)){
				nombreEstado = "Aprobado en facultad/instituto";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_FACULTAD) && avalFacultad!=null && avalFacultad.equals(Aval.NEGADO)){
				nombreEstado = "No aprobado en facultad/instituto";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_DIRECCION) && avalDireccion!=null && avalDireccion.equals(Aval.APROBADO)){
				nombreEstado = "Aprobado en dirección de investigación";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_DIRECCION) && avalDireccion!=null && avalDireccion.equals(Aval.NEGADO)){
				nombreEstado = "No aprobado en dirección de investigación";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_VICERRECTORIA) && avalVicerrectoria!=null && avalVicerrectoria.equals(Aval.APROBADO)){
				nombreEstado = "Aprobado en vicerrectoría de investigación";
			}else if(estadoAval!=null && estadoAval.equals(Aval.REVISADO_VICERRECTORIA) && avalVicerrectoria!=null && avalVicerrectoria.equals(Aval.NEGADO)){
				nombreEstado = "No aprobado en vicerrectoría de investigación";
			}else if(estadoAval!=null && estadoAval.equals(Aval.BORRADO) && avalDireccion!=null && avalVicerrectoria.equals(Aval.BORRADO)){
				nombreEstado = "Borrado en dirección de investigación";
			} else if (estadoAval != null && estadoAval.equals(Aval.REVISADO_DRE) && avalDRE != null && avalDRE.equals(Aval.APROBADO)) {
				nombreEstado = "Aprobado en Dirección de Relaciones Exteriores";
			} else if (estadoAval != null && estadoAval.equals(Aval.REVISADO_DRE) && avalDRE != null && avalDRE.equals(Aval.NEGADO)) {
				nombreEstado = "No Aprobado en Dirección de Relaciones Exteriores";
			} else if (estadoAval.equals(Aval.ETICO_ENVIADO_CEPI)) {
				nombreEstado = "Enviado a CEPI";
			} else if (estadoAval.equals(Aval.ETICO_APROBADO_CEPI)) {
				nombreEstado = "Aprobado CEPI";
			} else if (estadoAval.equals(Aval.ETICO_NO_APROBADO_CEPI)) {
				nombreEstado = "No aprobado CEPI";
			} else if (estadoAval.equals(Aval.ETICO_DEVUELTO_CEPI)) {
				nombreEstado = "Devuelto para correcciones CEPI";
			} else if (estadoAval.equals(Aval.ETICO_ENVIADO_CESI)) {
				nombreEstado = "Enviado a CESI";
			} else if (estadoAval.equals(Aval.ETICO_APROBADO_CESI)) {
				nombreEstado = "Aprobado CESI";
			} else if (estadoAval.equals(Aval.ETICO_NO_APROBADO_CESI)) {
				nombreEstado = "No aprobado CESI";
			} else if (estadoAval != null && estadoAval.equals(Aval.REVISADO_RECTORIA) && avalRectoria != null
					&& avalRectoria.equals(Aval.APROBADO)) {
				nombreEstado = "Aprobado en Rectoría";
			} else if(estadoAval!=null && estadoAval.equals(Aval.DEVUELTO_DRE)) {
				nombreEstado = "Devuelo para correcciones por DRE";
			} else if(estadoAval!=null && estadoAval.equals(Aval.DEVUELTO_RECTORIA)) {
				nombreEstado = "Devuelo para correcciones por Rectoría";
			}
			

		}catch (Exception e){
			e.printStackTrace();
		}
		
		return nombreEstado;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstadoAval() {
		return estadoAval;
	}

	public void setEstadoAval(String estadoAval) {
		this.estadoAval = estadoAval;
	}

	public String getAvalFacultad() {
		return avalFacultad;
	}

	public void setAvalFacultad(String avalFacultad) {
		this.avalFacultad = avalFacultad;
	}

	public String getAvalDireccion() {
		return avalDireccion;
	}

	public void setAvalDireccion(String avalDireccion) {
		this.avalDireccion = avalDireccion;
	}

	public String getAvalVicerrectoria() {
		return avalVicerrectoria;
	}

	public void setAvalVicerrectoria(String avalVicerrectoria) {
		this.avalVicerrectoria = avalVicerrectoria;
	}

	public Persona getRevisor() {
		return revisor;
	}

	public void setRevisor(Persona revisor) {
		this.revisor = revisor;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Aval getAval() {
		return aval;
	}

	public void setAval(Aval aval) {
		this.aval = aval;
	}

    public String getAvalUab() {
        return avalUab;
    }

    public void setAvalUab(String avalUab) {
        this.avalUab = avalUab;
    }

	public String getAvalDRE() {
		return avalDRE;
	}

	public void setAvalDRE(String avalDRE) {
		this.avalDRE = avalDRE;
	}
	
	public String getAvalRectoria() {
		return avalRectoria;
	}

	public void setAvalRectoria(String avalRectoria) {
		this.avalRectoria = avalRectoria;
	}
}
