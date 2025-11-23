
package co.edu.unal.hermes.vista.proyectos.evaluacion;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Evaluador;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;


/**
 * @author jassar

 */
public class ManejadorAsociacionEvaluadorProyecto extends ManejadorBase
{

	   //zona proyecto
	   private String 			idProyectoABuscar;
	   private Proyecto 		proyectoEncontrado;
	   private Proyecto         pryConEvals;//TODO TEMPORAL
	   private Proyecto         proySinEvals;//TODO TEMPORAL
	   private List 			listaEvaluadoresProyecto;
	   private List 			listaIdsEvaluadoresProyecto; //guarda los documentos de de los evaluadores del proyecto , para verificacion de adicion de evals que ya esten en la lista
	   private Set              listaEvaluadoresProyectoSet;//para pasarlo como parametro en la actuaizacion en BD de la lista de evaluadores del proyecto
	   private HtmlDataTable 	tablaListaEvaluadoresProyecto;
	   private boolean 			hayEvalsAsociados;
	   private boolean			existeProyecto;
	   private boolean 			noExistePersonaBuscada;
	   private boolean 			visibilidadBotonAsociar;
	   public String			avisoEventoProyecto;

	   //zona evaluadores
	   private HtmlDataTable    tablaEvaluadores;
	   private String 			idPersonaABuscar;
	   private String 			tipoDocumentoInvestigadorEscogido;
	   private int				tipoPersonaEncontrada;		        // 0 es evaluador, 1 es investigador, 2 no esta en el sistema
	   private Persona 			personaEncontrada;                  /*varia la acción a tomar si el retorno del primer servicio de búsqueda(servicio de búsqueda en tabla evaluadores).
	   																	si el resultado de este servicio es null, se ejecuta el segundo servicio de búsqueda( busqueda en tabla investigadores).*/
	   private Evaluador        evaluadorNuevo;                     //persona que no esta en el sistema y que será adicionada como un nuevo evaluador (una nueva persona en el sistema)
	   private SelectItem[]     tiposDocumentoItem;
	   private List   			listaTiposDocumento;
	   private SelectItem[]     listaGeneroItem;
	   private String			generoItemEscogido;
	   public String			avisoValidacionFormularioEval;
	   public String			avisoEventoEvaluador;


	   public ManejadorAsociacionEvaluadorProyecto()
	   {

	      System.out.println("ENTRÓ A CONSRTUCTOR");
	      valoresInicioProyecto();
	      valoresInicioEvaluador();
	      setTiposDocumentoItem();
	      setListaGeneroItem();
	   }

	  private void valoresInicioProyecto()
	  {
	      this.proyectoEncontrado= 		 		new Proyecto();

	      this.listaEvaluadoresProyecto= 		new ArrayList();
	      this.listaIdsEvaluadoresProyecto= 	new ArrayList();
	      this.tablaEvaluadores= 				new HtmlDataTable();
	      this.listaEvaluadoresProyectoSet=		new HashSet();
	      this.avisoEventoProyecto = " ";
	      //booleanos de renders
	      hayEvalsAsociados=false;
	      existeProyecto=false;
	      valoresInicioEvaluador();

	  }


	  private void valoresInicioEvaluador()
	  {
	  	this.personaEncontrada=  new Persona();
	  	this.evaluadorNuevo =  	 new Evaluador();
	  	this.noExistePersonaBuscada=false;
	  	this.visibilidadBotonAsociar=false;
	  	this.avisoValidacionFormularioEval="";
	  	this.avisoEventoEvaluador="";

	  }
	  public void buscarProyecto()
	   {
	  	   valoresInicioProyecto();
	  	   this.proyectoEncontrado = servicioProyecto.obtenerProyecto(new Long(idProyectoABuscar), ProyectoDAOHibernate.EVALUADORES);
	       if(this.proyectoEncontrado!=null)
	       {
	           this.existeProyecto=true;
	           if( !this.proyectoEncontrado.getEvaluadoresProyecto().isEmpty())
	 	      {

	           	  this.listaEvaluadoresProyecto= 		new ArrayList(this.proyectoEncontrado.getEvaluadoresProyecto());
	              this.listaEvaluadoresProyectoSet = 	this.proyectoEncontrado.getEvaluadoresProyecto();
	              llenarListaDeIdsEvaluadores();   //crear un arreglo con los strings documentos de los evaluadores
	              this.hayEvalsAsociados=true;
	 	      }else
	 	      {
	 	      	System.out.println("no tiene evauadores");
	 	      }
          }else
	       {
          	 this.avisoEventoProyecto="El código ingresado no pertenece a ningún proyecto registrado en el sistema.";
	       }
	   }

	  private void llenarListaDeIdsEvaluadores()    		//guarda los ids (Strings) de los evaluadores del proyecto
	  {
	  	if(this.listaEvaluadoresProyecto.size()>0)
	  	{
		  	for(int i=0;i<this.listaEvaluadoresProyecto.size();++i)
		  	{
		  		String id = new String(((ProyectoEvaluador)this.listaEvaluadoresProyecto.get(i)).getEvaluador().getId().getDocumento());
		  		System.out.println(id);
		  		this.listaIdsEvaluadoresProyecto.add(id);
		  	}
	  	}
	  }
	   public void buscarPersona()
	   {

	   	   valoresInicioEvaluador();
	       this.visibilidadBotonAsociar=true; 			//si no encuentra la persona aparecerá el formulario y con él tambien el botón asociar

	        IdPersona id1 = new IdPersona();
		 	id1.setDocumento(idPersonaABuscar);
		 	id1.setTipoDocumento(tipoDocumentoInvestigadorEscogido);

		 	 //1RA BUSQUEDA: tabla evaluadores
		 	 Evaluador eval = servicioPersona.obtenerEvaluador(id1);

		 	 if(eval != null)
		 	 {
		 	     this.personaEncontrada= eval;
		 	    this.tipoPersonaEncontrada=0;
		 	     System.out.println("es evaluador");
		 	 }else
		 	 {
		 	     //2DA BUSQUEDA: tabla investigadores
		 	     Investigador inv= servicioPersona.obtenerInvestigador(id1);
		 	     if(inv!=null)
		 	     {
		 	        System.out.println("es investigador");
		 	         this.personaEncontrada= inv;
		 	         this.tipoPersonaEncontrada=1;

		 	     }else
		 	     {
		 	     	 System.out.println("no està");
		 	         this.tipoPersonaEncontrada=2;
		 	         this.noExistePersonaBuscada=true;

		 	     }
		 	 }
	 }
	   private void guardaSiEsEvaluador(Persona persona)
	   {
	   	Evaluador evalu = new Evaluador();
	   	evalu= (Evaluador)persona;

	   	servicioGeneral.guardarObjeto(evalu);   //aunque ya esta en el sistema como evaluador, vuelve a guadarlo para actualizar datos: palabras clave. Inserta en las tres tablas persona, investigador, evaluador

 	 	actualizaListaJSFYSetEvaluadoresProyecto(evalu);
	   }

	   private void guardaSiEsInvestigador(Persona persona)
	   {
	       //TODO revisar que hace con la persona cuando la convierte en evaluador
	       /*
		   	Evaluador evalu = new Evaluador();
		   	evalu= ((Investigador)persona).convertirEnEvaluador();  //retorna un objeto evaludaor
		 	evalu.setEvaluador("S");
		 	
		 	servicioPersona.insertarEvaluador(evalu);        //solo inserta en la tabla evaluador
	
		 	actualizaListaJSFYSetEvaluadoresProyecto(evalu);
		 	*/
	       
	   }

	   private void guardaSiPersonaNoEsta(Persona persona)
	   {
	       // TODO juan revisar 
	       /*
	   	Evaluador evalu = new Evaluador();

	   	evalu=this.evaluadorNuevo;
	 	 evalu.setEvaluador("S");
	 	 evalu.setInterno("N");
	 	*/
	 	try
		{
	 	   servicioGeneral.insertarObjeto(persona);			//ingresa un nuevo evaluador. Inserta en las tres tablas persona, investigador, evaluador
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		//actualizaListaJSFYSetEvaluadoresProyecto(evalu);
	   }

	 public void asociarEvaluadorAProyecto()
	   {
	     
	 	//1. se crea un nuevo objeto evaluador y u nuevo objeto ProyectoEvaluador
	 	//2. se determina que tipo de objeto viene dentro del objeto Persona this.personaEncontrada, si es Evaluador, o Investigador, o si no se encontrò la persona: se toman los datos del formulario de evaluador nuevo( que llenan el objeto this.evaluadorNuevo) y se agraga este ala BD
	 	//3. se llena el objeto proyEval y se adiciona a la lista this.listaEvaluadoresProyecto (jsf) y su correspondiente set se pasa a this.proyectoEncontrado
	 	//4. se actualiza el pry en BD


	 	if(this.personaEncontrada!=null && this.listaIdsEvaluadoresProyecto.contains(this.personaEncontrada.getId().getDocumento()))  //verifica si el eval ya esta asociado al proyecto
	       {
	       	mostrarListaIds();
	 	 	System.out.println("ya fue adicionado a al lista");
	 	 	this.avisoEventoEvaluador="este evaluador ya fué asociado al proyecto.";

	       }else
	       {
			 	switch(this.tipoPersonaEncontrada)
				{
				 	 case 0: //viene un objeto evaluador
					 {
				 	 	System.out.println("case 0");
				 	 	guardaSiEsEvaluador(this.personaEncontrada);
				 	 	break;
					 }
					 case 1: //viene un objeto investigador
					 {
					 	System.out.println("case 1");
					 	guardaSiEsInvestigador(this.personaEncontrada);
					 	break;
					 }
					 case 2: //no encuentra la persona en el sistema
					 {
					 	if(validarFormularioEvaluador()) guardaSiPersonaNoEsta(this.personaEncontrada);
						 break;
					 }

				}

			 	valoresInicioEvaluador();
			 	this.idPersonaABuscar="";
			   }

	   }

	private void actualizaListaJSFYSetEvaluadoresProyecto(Evaluador evalu)
	{
		 ProyectoEvaluador proyEval= new ProyectoEvaluador();

		 proyEval.setEvaluador(evalu);
	 	 proyEval.setProyecto(this.proyectoEncontrado);
	 	 Object obj=proyEval;
	     this.listaEvaluadoresProyecto.add(obj);
	     this.hayEvalsAsociados=true;
	     this.listaEvaluadoresProyectoSet.add(obj);
	     this.listaIdsEvaluadoresProyecto.add(evalu.getId().getDocumento()); //adiciona cedula a la lista de cedulas adicionadas

	     //4.
		actualizarListaEvaluadoresProyectoEnBD(this.listaEvaluadoresProyectoSet);
		mostrarListaIds();
		valoresInicioEvaluador();
	}
	private void actualizarListaEvaluadoresProyectoEnBD(Set listaEvalsproySet)
	{
		   this.proyectoEncontrado.setEvaluadoresProyecto(listaEvalsproySet);		   
		   servicioProyecto.actualizarProyecto(this.proyectoEncontrado);
	}

	 public  void quitarEvaluadorDeProyecto()
	   {
	 	int posicion= this.tablaEvaluadores.getRowIndex();
	 	ProyectoEvaluador evaluadorAQuitar= new ProyectoEvaluador();
	 	evaluadorAQuitar=(ProyectoEvaluador)this.listaEvaluadoresProyecto.get(posicion);
	 	System.out.println("evaluadorAQuitar :" +evaluadorAQuitar.getEvaluador().getId().getDocumento());
	 	this.listaEvaluadoresProyecto.remove(this.listaEvaluadoresProyecto.get(posicion));//remueve de la lista jsf
	    // remueve del set que serà pasado para actualizar el pry en BD
	    Iterator iter= this.listaEvaluadoresProyectoSet.iterator();

	     while(iter.hasNext())
	     {
	         Object obj = iter.next();
	         if( evaluadorAQuitar.getEvaluador().getId().getDocumento().equals(((ProyectoEvaluador)obj).getEvaluador().getId().getDocumento())){
	         	this.listaEvaluadoresProyectoSet.remove(obj);
	        	for(int u=0;u< this.listaIdsEvaluadoresProyecto.size();++u) //remueve de la lista de ids
	    	 	{
	    	 		if(this.listaIdsEvaluadoresProyecto.get(u).equals(((ProyectoEvaluador)obj).getEvaluador().getId().getDocumento()))
	    	 			this.listaIdsEvaluadoresProyecto.remove(u);
	    	 	}
	            break;
	         }

	     }
	     actualizarListaEvaluadoresProyectoEnBD(this.listaEvaluadoresProyectoSet); //actualiza el pry en BD
	     mostrarListaIds();
	   }


	public void setTiposDocumentoItem()
		{
				   this.listaTiposDocumento= servicioGeneral.obtenerListaObjetos("TipoDocumento");
			       this.tiposDocumentoItem= new   SelectItem[this.listaTiposDocumento.size()];
			       int m=0;
			        for (int i = 0; i < this.listaTiposDocumento.size(); i++)
			        {
			          	TipoDocumento td = (TipoDocumento) this.listaTiposDocumento.get(i);
			        	this.tiposDocumentoItem[m] = new SelectItem(td.getId(), td.getNombre());
				     m++;
			        	td=null;
			        }
		}

	private boolean validarFormularioEvaluador()
	{
		//validación de datos
		boolean resul=true;
		this.avisoValidacionFormularioEval="";
		IdPersona idP= new IdPersona();
		idP.setDocumento(idPersonaABuscar);
		idP.setTipoDocumento(tipoDocumentoInvestigadorEscogido);
		this.evaluadorNuevo.setId(idP);
		try
		{

		if(this.evaluadorNuevo.getApellido1().equals("") ||   this.evaluadorNuevo.getNombre1().equals("") | (this.evaluadorNuevo.getEmail().equals("") & this.evaluadorNuevo.getTelefono().equals("")))
		{
			resul=false;
			this.avisoValidacionFormularioEval= "La información referente a: ";

			/*if(this.evaluadorNuevo.getId().getDocumento().equals(""))
				{

					this.avisoValidacionFormularioEval+= "ID, ";

				}*/

			if(this.evaluadorNuevo.getNombre1().equals(""))
				{
					this.avisoValidacionFormularioEval+= "1er nombre, ";
				}


			if(this.evaluadorNuevo.getApellido1().equals(""))
				{
					this.avisoValidacionFormularioEval+= "1er apellido, ";

				}


			if(this.evaluadorNuevo.getEmail().equals("") & this.evaluadorNuevo.getTelefono().equals(""))
				{

					this.avisoValidacionFormularioEval+= "Teléfono, email, ";

				}
			this.avisoValidacionFormularioEval+="no ha sido ingresada, esta es necesaria.";


		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return resul;


	}
	//TEMPORAL
	private void mostrarListaIds()
	{
		for(int y =0;y<listaIdsEvaluadoresProyecto.size();y++)
		{
			System.out.println("ids :" +listaIdsEvaluadoresProyecto.get(y) );
		}
	}
   public SelectItem[] getTiposDocumentoItem()
		{
			return tiposDocumentoItem;
		}

   private void setListaGeneroItem()
   {

		  this.listaGeneroItem= new SelectItem[2];
		  this.listaGeneroItem[0] = new SelectItem("0",Persona.GENERO_MASCULINO);
		  this.listaGeneroItem[1] = new SelectItem("1",Persona.GENERO_FEMENINO);
    }
    public String getGeneroItemEscogido()
    {
	   return generoItemEscogido;
    }

   public Evaluador getEvaluadorNuevo() {
		return evaluadorNuevo;
	}
	public void setEvaluadorNuevo(Evaluador evaluadorNuevo) {
		this.evaluadorNuevo = evaluadorNuevo;
	}
	public String getIdPersonaABuscar() {
		return idPersonaABuscar;
	}
	public void setIdPersonaABuscar(String idPersonaABuscar) {
		this.idPersonaABuscar = idPersonaABuscar;
	}
	public String getIdProyectoABuscar() {
		return idProyectoABuscar;
	}
	public void setIdProyectoABuscar(String idProyectoABuscar) {
		this.idProyectoABuscar = idProyectoABuscar;
	}
	public List getListaEvaluadoresProyecto() {
		return listaEvaluadoresProyecto;
	}
	public void setListaEvaluadoresProyecto(List listaEvaluadoresProyecto) {

	    this.listaEvaluadoresProyecto = listaEvaluadoresProyecto;

	}
	public Persona getPersonaEncontrada() {
		return personaEncontrada;
	}
	public void setPersonaEncontrada(Persona personaEncontrada) {
		this.personaEncontrada = personaEncontrada;
	}
	public HtmlDataTable getTablaListaEvaluadoresProyecto() {
		return tablaListaEvaluadoresProyecto;
	}
	public void setTablaListaEvaluadoresProyecto(
			HtmlDataTable tablaListaEvaluadoresProyecto) {
		this.tablaListaEvaluadoresProyecto = tablaListaEvaluadoresProyecto;
	}
	public String getTipoDocumentoInvestigadorEscogido() {
		return tipoDocumentoInvestigadorEscogido;
	}
	public void setTipoDocumentoInvestigadorEscogido(
			String tipoDocumentoInvestigadorEscogido) {
		this.tipoDocumentoInvestigadorEscogido = tipoDocumentoInvestigadorEscogido;
	}

   public boolean getHayEvalsAsociados() {
        return hayEvalsAsociados;
    }
    public void setHayEvalsAsociados(boolean hayEvalsAsociados) {
        this.hayEvalsAsociados = hayEvalsAsociados;
    }

   public Proyecto getProyectoEncontrado() {
        return proyectoEncontrado;
    }
    public void setProyectoEncontrado(Proyecto proyectoEncontrado) {
        this.proyectoEncontrado = proyectoEncontrado;
    }


    public boolean getExisteProyecto() {
        return existeProyecto;
    }
    public void setExisteProyecto(boolean existeProyecto) {
        this.existeProyecto = existeProyecto;
    }

    public boolean getNoExistePersonaBuscada() {
        return noExistePersonaBuscada;
    }
    public void setNoExistePersonaBuscada(boolean noExistePersonaBuscada) {
        this.noExistePersonaBuscada = noExistePersonaBuscada;
    }
    public boolean getVisibilidadBotonAsociar() {
        return visibilidadBotonAsociar;
    }
    public void setVisibilidadBotonAsociar(boolean visibilidadBotonAsociar) {
        this.visibilidadBotonAsociar = visibilidadBotonAsociar;
    }

   public SelectItem[] getListaGeneroItem() {
        return listaGeneroItem;
    }

    public void setGeneroItemEscogido(String generoItemEscogido) {
        this.generoItemEscogido = generoItemEscogido;
    }
	public HtmlDataTable getTablaEvaluadores() {
		return tablaEvaluadores;
	}
	public void setTablaEvaluadores(HtmlDataTable tablaEvaluadores) {
		this.tablaEvaluadores = tablaEvaluadores;
	}
	public String getAvisoEventoProyecto() {
		return avisoEventoProyecto;
	}
	public void setAvisoEventoProyecto(String avisoEventoProyecto) {
		this.avisoEventoProyecto = avisoEventoProyecto;
	}
	public String getAvisoValidacionFormularioEval() {
		return avisoValidacionFormularioEval;
	}
	public void setAvisoValidacionFormularioEval(
			String avisoValidacionFormularioEval) {
		this.avisoValidacionFormularioEval = avisoValidacionFormularioEval;
	}
	public String getAvisoEventoEvaluador() {
		return avisoEventoEvaluador;
	}
	public void setAvisoEventoEvaluador(String avisoEventoEvaluador) {
		this.avisoEventoEvaluador = avisoEventoEvaluador;
	}
}
