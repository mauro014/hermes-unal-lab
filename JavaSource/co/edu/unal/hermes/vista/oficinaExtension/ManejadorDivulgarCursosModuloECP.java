package co.edu.unal.hermes.vista.oficinaExtension;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorDivulgarCursosModuloECP  extends ManejadorBase{
	
	private Proyecto cursoSeleccionado;
	
	private String sede; // = "2";  Bogotá
	private String facultad; // = "2055"; Ingeniería
	private String departamento; // = "2368";  Sistemas	
	
	private String cuerpoCorreo;
	
	private boolean enviaEstudiantes;
	private boolean enviaProfesores;
	private boolean enviaAdministrativos;
	
	//
	private Proyecto cursoActual;
	private List listaSeleccionadosPoblacion;
	private List listaOpcionesPoblacion;
	private String asuntoCorreo;
	private String correoPrueba;
		
	public ManejadorDivulgarCursosModuloECP() {
		
		cursoActual = (Proyecto) sesion.getAttribute("cursoECP");
		cargarOpcionesPoblacion();
		cargarAsunto();
		cargarCuerpo();
		
		Persona persona = (Persona) sesion.getAttribute("persona");
		
		correoPrueba = persona.getEmail();
		
		
		listaSeleccionadosPoblacion = new ArrayList<String>();
		listaSeleccionadosPoblacion.add("ES");
		listaSeleccionadosPoblacion.add("AD");
		listaSeleccionadosPoblacion.add("DO");

	}
	
	public void enviarCorreo()
	{
		try
		{
			
			if(listaSeleccionadosPoblacion.size() > 0)
			{	
				for(int i=0;i<listaSeleccionadosPoblacion.size();i++)
				{	
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
					
					correo.setAsunto(asuntoCorreo);
					correo.setCuerpo(cuerpoCorreo);
					
					if(listaSeleccionadosPoblacion.get(i).equals("ES"))
					{
						correo.adicionarDireccion("estudiantes@unal.edu.co");
						System.out.println("Se envia correo a ESTUDIANTES ");
						
					}	else if(listaSeleccionadosPoblacion.get(i).equals("DO"))
						{
							correo.adicionarDireccion("docentes@unal.edu.co");
							System.out.println("Se envia correo a DOCENTES ");
							
						}	else if(listaSeleccionadosPoblacion.get(i).equals("AD"))
							{
								correo.adicionarDireccion("administrativos@unal.edu.co");
								System.out.println("Se envia correo a ADMINISTRATIVOS ");
								
							}
						
					if(servicioCorreo.enviarCorreo(correo))
					{
						FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								"Correo enviado correctamente",
								""));
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Ha ocurrido un problema enviando el correo",
								""));
					}
					
					}	
					
				}	
			
			else
			{
				FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Para enviar un correo de prueba debe especificar un email valido",
								""));
			}
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public void enviarCorreoPrueba()
	{
		try
		{
		
			if(correoPrueba != null && !correoPrueba.equals(""))
			{	
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(correoPrueba);
		
				//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
		
				correo.setAsunto(asuntoCorreo);
				correo.setCuerpo(cuerpoCorreo);
				
				if(servicioCorreo.enviarCorreo(correo))
				{
					FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Correo de prueba enviado correctamente",
							""));
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Ha ocurrido un problema enviando el correo de prueba",
							""));
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Para enviar un correo de prueba debe especificar un email valido",
								""));
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
			
	}
	
	public void cargarAsunto()
	{
		if(cursoActual != null)
			asuntoCorreo = "INVITACION CURSO - "+cursoActual.getNombre();
		else
			System.out.println("Curso NULO como Parametro - Metodo cargarAsunto()");
	}
	
	private String cambiarFormatoFecha(java.util.Date date)
	{
		DateFormat df3 = DateFormat.getDateInstance(DateFormat.LONG);
		return df3.format(date);
	}
	
	private String cambiarFormatoValor(Long valor)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
		
		DecimalFormat decf = new DecimalFormat("$#,###,###");
		
		//valorFormato = nf.format(valor);
		return decf.format(valor);
	}
	
	public void cargarCuerpo()
	{
		if(cursoActual != null)
		{	
			cuerpoCorreo = "La Universidad Nacional de Colombia lo invita a participar del curso: " +
					"\n\n" +
					"\tNombre:\t"+cursoActual.getNombre()+
					"\n" +
					"\tObjetivo:\t"+cursoActual.getObjetivoGeneral()+
					"\n" +
					"\tMetodología:\t"+cursoActual.getMetodologia()+
					"\n" +
					"\tFecha Inicio:\t"+ cambiarFormatoFecha(cursoActual.getFechaFinalizacion())+
					"\n" +
					"\tFecha Finalización:\t"+cambiarFormatoFecha(cursoActual.getFechaFinalizacion())+
					"\n" +
					"\tValor inscripción:\t"+cambiarFormatoValor(Long.parseLong(cursoActual.getCostoPersona()))+
					"\n" +
					"\tDependencia:\t"+cursoActual.getDependencia().getNombre()+
					"\n" +
					"\tDocente:\t"+cursoActual.getResponsable().getNombre1()+" "+cursoActual.getResponsable().getNombre2()+" "+cursoActual.getResponsable().getApellido1()+" "+cursoActual.getResponsable().getApellido2()+
					"\n\n" +
					"Mas información y Preinscripciones: http://www.hermes.unal.edu.co/pages/Consultas/CursoECP.xhtml?idCurso="+cursoActual.getId();
			
		}	
		else
			System.out.println("Curso NULO como Parametro - Metodo cargarCuerpo()");
	}
	
	public void cargarOpcionesPoblacion()
	{
		listaOpcionesPoblacion = new ArrayList<SelectItem>();
		listaOpcionesPoblacion.add(new SelectItem("ES","Estudiantes"));
		listaOpcionesPoblacion.add(new SelectItem("AD","Administrativos"));
		listaOpcionesPoblacion.add(new SelectItem("DO","Docentes"));
	}

	public Proyecto getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	public void setCursoSeleccionado(Proyecto cursoSeleccionado) {
		this.cursoSeleccionado = cursoSeleccionado;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public boolean isEnviaEstudiantes() {
		return enviaEstudiantes;
	}

	public void setEnviaEstudiantes(boolean enviaEstudiantes) {
		this.enviaEstudiantes = enviaEstudiantes;
	}

	public boolean isEnviaProfesores() {
		return enviaProfesores;
	}

	public void setEnviaProfesores(boolean enviaProfesores) {
		this.enviaProfesores = enviaProfesores;
	}

	public boolean isEnviaAdministrativos() {
		return enviaAdministrativos;
	}

	public void setEnviaAdministrativos(boolean enviaAdministrativos) {
		this.enviaAdministrativos = enviaAdministrativos;
	}

	public Proyecto getCursoActual() {
		return cursoActual;
	}

	public void setCursoActual(Proyecto cursoActual) {
		this.cursoActual = cursoActual;
	}

	public List getListaSeleccionadosPoblacion() {
		return listaSeleccionadosPoblacion;
	}

	public void setListaSeleccionadosPoblacion(List listaSeleccionadosPoblacion) {
		this.listaSeleccionadosPoblacion = listaSeleccionadosPoblacion;
	}

	public List getListaOpcionesPoblacion() {
		return listaOpcionesPoblacion;
	}

	public void setListaOpcionesPoblacion(List listaOpcionesPoblacion) {
		this.listaOpcionesPoblacion = listaOpcionesPoblacion;
	}

	public String getAsuntoCorreo() {
		return asuntoCorreo;
	}

	public void setAsuntoCorreo(String asuntoCorreo) {
		this.asuntoCorreo = asuntoCorreo;
	}

	public String getCorreoPrueba() {
		return correoPrueba;
	}

	public void setCorreoPrueba(String correoPrueba) {
		this.correoPrueba = correoPrueba;
	}
	
	
	
}
