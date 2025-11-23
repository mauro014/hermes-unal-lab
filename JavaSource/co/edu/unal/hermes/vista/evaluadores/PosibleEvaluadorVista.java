package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.ManejadorBase;

public class PosibleEvaluadorVista extends ManejadorBase{
	
    private boolean colapsado;
    private boolean escogido;
	private String nombre;
	private String telefono;
	private String email;
	private String experticia;
	private boolean externo;
	private String documento;
	private String tipoDocumento;
	private String institucion;
	private List   listaClasificacionConocimiento;
	private String nombreProyecto;
	private String urlHojaVidaCVLAC;
	private IdPersona idPersonaPosibleEvaluadorExterno;
	private Dependencia dependenciaInterno;
	private Long id;
	private String nombre1;
	private String apellido1;
	private String apellido2;
	
	private SelectItem[]	correos;
    private String 			correoId;
    private CorreoPlantilla	correoActual;
	private String correoActualCuerpo;
	private boolean detalle;
	
	
	
	//CONSTRUCTOR DE EVALUADOR VISTA PARA EVALUADORES EXTERNOS 
	public PosibleEvaluadorVista(PosibleEvaluador pe,String nombProyecto){
	    this.colapsado    = true;
	    this.escogido     = false;
		this.nombre       =pe.getNombre();
		if(pe.getApellido1()!=null){
		    this.nombre += " "+pe.getApellido1();
		}
		if(pe.getApellido2()!=null){
		    this.nombre += " "+pe.getApellido2();
		}
		this.telefono     =pe.getTelefono();
		this.email        =pe.getEmail();				
		this.externo=true;
		this.documento = pe.getDocumento();
		this.tipoDocumento=pe.getTipoDocumento();
//		IdPersona ip= new IdPersona();
//		ip.setDocumento(pe.getDocumento());
//		ip.setTipoDocumento(pe.getTipoDocumento());
//		this.idPersonaPosibleEvaluadorExterno=ip;
		this.institucion=pe.getInstitucion();
		listaClasificacionConocimiento=new ArrayList();
		listaClasificacionConocimiento.add(pe.getClasificacionConocimiento());
		List correosL=servicioGeneral.obtenerListaObjetos("CorreoPlantilla");
       correos=new SelectItem[correosL.size()];
       int i=0;
       for(Iterator ic=correosL.iterator();ic.hasNext();i++)
       {
            CorreoPlantilla p= (CorreoPlantilla)ic.next();
            correos[i]=new SelectItem(p.getId().toString(),p.getNombre());
       }
       correoId=((CorreoPlantilla)(correosL.get(0))).getId().toString();
       correoActual=(CorreoPlantilla)(correosL.get(0));nombreProyecto=nombProyecto;
       reemplazarCuerpo();
}
	
    //CONSTRUCTOR DE EVALAUDOR VISTA PARA EVALUADORES
	public PosibleEvaluadorVista(Investigador ie,String nombProyecto){
	    this.colapsado    = true;
	    this.escogido     = false;
		this.nombre       =ie.getNombre1();
		if(ie.getNombre2()!=null){
		    this.nombre += " "+ie.getNombre2();
		}
		this.nombre += " "+ie.getApellido1();
		if(ie.getApellido2()!=null){
		    this.nombre += " "+ie.getApellido2();
		}		    
		this.telefono     =ie.getTelefono();
		this.email        =ie.getEmail();
		if(ie.getInterno().equals("S"))
		{
			this.externo=false;		    
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInternoDependenciaYFacultad(ie.getId());
			dependenciaInterno=ii.getDependencia();
		}
		else
		{
		    this.externo=true;
		}
		    
		this.documento =ie.getId().getDocumento();
		this.tipoDocumento=ie.getId().getTipoDocumento();
		this.institucion="";
		listaClasificacionConocimiento=new ArrayList();
		listaClasificacionConocimiento.addAll(ie.getClasificacionesConocimiento());
		List correosL=servicioGeneral.obtenerListaObjetos("CorreoPlantilla");
	       correos=new SelectItem[correosL.size()];
	       int i=0;
	       for(Iterator ic=correosL.iterator();ic.hasNext();i++)
	       {
	            CorreoPlantilla p= (CorreoPlantilla)ic.next();
	            correos[i]=new SelectItem(p.getId().toString(),p.getNombre());
	       }
	       correoId=((CorreoPlantilla)(correosL.get(0))).getId().toString();
	       correoActual=(CorreoPlantilla)(correosL.get(0));
	       nombreProyecto=nombProyecto;
	       reemplazarCuerpo(); 
	       setUrlHojaVidaCVLAC(ie.getUrlColciencias());
	}
	
	public void cambiarCorreo(ValueChangeEvent event)
    {
        String idCorreo=(String)event.getNewValue();
        for(int i=0;i<correos.length;i++)
        {
            SelectItem c=correos[i];
            if(((String)c.getValue()).equals(idCorreo))
            {
                CorreoPlantilla a= new CorreoPlantilla();
                correoActual=(CorreoPlantilla) servicioGeneral.obtenerObjeto(a,Long.valueOf(idCorreo));
                
                String correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
                correo=correo.replaceAll("<<nombreEvaluador>>",nombre==null?"<<nombreEvaluador>>":nombre);
                correo=correo.replaceAll("<<nombreProyecto>>",nombreProyecto==null?"<<nombreProyecto>>":nombreProyecto);
                Dependencia departamento = servicioDependencia.obtenerDependencia(dependenciaInterno==null || dependenciaInterno.getDepartamento()==null?"sin departamento":dependenciaInterno.getDepartamento());
                String nombreDependencia = (departamento==null?"sin departamento":departamento.getNombre());
                correo=correo.replaceAll("<<departamento>>",nombreDependencia             );
                correo=correo.replaceAll("<<facultad>>",dependenciaInterno==null || dependenciaInterno.getFacultad()==null?"sin facultad":dependenciaInterno.getFacultad().getNombre());
                
                IdPersona id = new IdPersona();
                id.setDocumento(documento);
                id.setTipoDocumento(tipoDocumento);
                InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(id);
                System.out.println("buscar ie con cc "+getTipoDocumento() + getDocumento()); 

                
                if(ie!=null)
                {		            
        	        if(ie.getContrasena()==null)
        	        {
        	            ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
        	        }
        	        correo=correo.replaceAll("<<usuario>>",servicioPersona.login(ie));
        	        correo=correo.replaceAll("<<clave>>",ie.getContrasena().toString());//ie.getId().getDocumento()+"h"));
        	        servicioGeneral.guardarObjeto(ie);
                }
                //correo=correo.replaceAll("<<nombreProyecto>>",nombreProyecto==null?"<<nombreProyecto>>":nombreProyecto);
                System.out.println(System.getProperty("line.separator"));
//                correo=correo.replaceAll("\\"+System.getProperty("line.separator"),"<br/>");
//                correoActual.setCuerpo(correo);
                
                correoActualCuerpo=correo;
                break;
            }
        }	
        
    }
	
	public void reemplazarCuerpo()
	{
	    String correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
	    correo=correo.replaceAll("<<nombreEvaluador>>",nombre==null?"<<nombreEvaluador>>":nombre);
        correo=correo.replaceAll("<<nombreProyecto>>",nombreProyecto==null?"<<nombreProyecto>>":nombreProyecto);
        Dependencia departamento = servicioDependencia.obtenerDependencia(dependenciaInterno==null || dependenciaInterno.getDepartamento()==null?"sin departamento":dependenciaInterno.getDepartamento());
        String nombreDependencia = departamento==null?"sin departamento":departamento.getNombre();
       
        correo=correo.replaceAll("<<departamento>>",nombreDependencia);
        correo=correo.replaceAll("<<facultad>>",dependenciaInterno==null || dependenciaInterno.getFacultad() ==null?"sin facultad":dependenciaInterno.getFacultad().getNombre());
        System.out.println(System.getProperty("line.separator"));
        
        IdPersona id = new IdPersona();
        id.setDocumento(documento);
        id.setTipoDocumento(tipoDocumento);
        InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(id);
        System.out.println("buscar ie con cc "+getTipoDocumento() + getDocumento()); 

        
        if(ie!=null)
        {		            
	        if(ie.getContrasena()==null)
	        {
	            ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
	        }
	        correo=correo.replaceAll("<<usuario>>",servicioPersona.login(ie));
	        correo=correo.replaceAll("<<clave>>",ie.getContrasena().toString());//ie.getId().getDocumento()+"h"));
	        servicioGeneral.guardarObjeto(ie);
        }
//        correo=correo.replaceAll("\\"+System.getProperty("line.separator"),"<br/>");
//        correoActual.setCuerpo(correo);
        
        correoActualCuerpo=correo;
	}
	
	public String editar()
	{
	    System.out.println("--------------------");
	    System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
	    System.out.println("-------------editar ");
		setDetalle(!getDetalle());
		return "";
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}		
	
    public boolean isExterno() {
        return externo;
    }
    public void setExterno(boolean externo) {
        this.externo = externo;
    }
    public boolean isColapsado() {
        return colapsado;
    }
    public void setColapsado(boolean colapsado) {
        this.colapsado = colapsado;
    }
    
    public boolean isEscogido() {
        return escogido;
    }
    public void setEscogido(boolean escogido) {
        this.escogido = escogido;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public String getInstitucion() {
        return institucion;
    }
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
	/**
	 * @return Returns the listaClasificacionConocimiento.
	 */
	public List getListaClasificacionConocimiento() {
		return listaClasificacionConocimiento;
	}
	/**
	 * @param listaClasificacionConocimiento The listaClasificacionConocimiento to set.
	 */
	public void setListaClasificacionConocimiento(
			List listaClasificacionConocimiento) {
		this.listaClasificacionConocimiento = listaClasificacionConocimiento;
	}
    public CorreoPlantilla getCorreoActual() {
        
        
      
        return correoActual;
    }
    public void setCorreoActual(CorreoPlantilla correoActual) {
        this.correoActual = correoActual;
    }
    public String getCorreoId() {
        return correoId;
    }
    public void setCorreoId(String correoId) {
        this.correoId = correoId;
    }
    public SelectItem[] getCorreos() {
        return correos;
    }
    public void setCorreos(SelectItem[] correos) {
        this.correos = correos;
    }
    public String getCorreoActualCuerpo() {
        return correoActualCuerpo;
    }
    public void setCorreoActualCuerpo(String correoActualCuerpo) {
        this.correoActualCuerpo = correoActualCuerpo;
    }

	/**
	 * @return Returns the detalle.
	 */
	public boolean getDetalle() {
		return detalle;
	}
	/**
	 * @param detalle The detalle to set.
	 */
	public void setDetalle(boolean detalle) {
		this.detalle = detalle;
	}
    public String getNombreProyecto() {
        return nombreProyecto;
    }
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public void setUrlHojaVidaCVLAC(String urlHojaVidaCVLAC) 
    {
        this.urlHojaVidaCVLAC=urlHojaVidaCVLAC;
    }
    public String getUrlHojaVidaCVLAC() {
        return urlHojaVidaCVLAC;
    }
    public IdPersona getIdPersonaPosibleEvaluadorExterno() {
        return idPersonaPosibleEvaluadorExterno;
    }
    public void setIdPersonaPosibleEvaluadorExterno(
            IdPersona idPersonaPosibleEvaluadorExterno) {
        this.idPersonaPosibleEvaluadorExterno = idPersonaPosibleEvaluadorExterno;
    }
    public Dependencia getDependenciaInterno() {
        return dependenciaInterno;
    }
    public void setDependenciaInterno(Dependencia dependenciaInterno) {
        this.dependenciaInterno = dependenciaInterno;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getExperticia() {
		return experticia;
	}

	public void setExperticia(String experticia) {
		this.experticia = experticia;
	}
}
