/**
 * @author  Ing Hernán Darío Bernal Parra
 */

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorInformacionMarco extends ManejadorProyecto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Coleccion> listaColecciones;
	private List<InvestigadorProyecto> listaDirector;
	private SelectItem[] listaColeccionesItem;
	private SelectItem[] ambitoRecolecta;
	private SelectItem[] areasGeograficas;
	private SelectItem[] actividades;
	private String coleccionSeleccionadaId;
	private String ambitoId;
	private String actividadesId = "4";
	
	private Boolean opcion0 = false;
	
	private Boolean opcion1 = false;
	
	private Boolean opcion2 = false;
	
	private Boolean opcion3 = false;
	
	private Boolean opcion4 = false;
	
	private Boolean opcion5 = false;
	
	private String jurisdiccion;
	
	private String areaGeografica;
	
	private String categoriaTaxonomicaMinima;
	
	private Proyecto proyectoAsociar;
	
	public ManejadorInformacionMarco() {
		super();
		cargarValoresIniciales();
		cargarColeccionesPermitidas();
		cargarAmbito();
		cargarAreaGeogragica();
		cargarActividades();
		cargarOpciones();
	}

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
		listaDirector = new ArrayList<InvestigadorProyecto>();
		try{
			proyectoAsociar = (Proyecto) sesion.getAttribute("proyectoAsociar");
			sesion.removeAttribute("proyectoAsociar");
		}catch (Exception e){
			
		}
			
			if(proyectoAsociar != null){
				proyectoActual.getModalidad().setId(MODALIDAD_PERMISO_MARCO_2024);
				proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
				proyectoActual.setCodigoDib(proyectoAsociar.getId().toString());
				proyectoActual.setNombre("Registro proyecto Permiso Marco de recolección: '"+ proyectoAsociar.getNombre()+"'");
				proyectoActual.setFase(0);
				proyectoActual.setDuracion(proyectoAsociar.getDuracion());
				if(proyectoAsociar.getFechaTentativaInicio()!=null){
					proyectoActual.setFechaTentativaInicio(proyectoAsociar.getFechaTentativaInicio());
				}
			}
			
			if(proyectoActual.getId()==null || proyectoActual.getId()==0){
				if(proyectoActual.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO)){
					proyectoActual.setAmbitoGeneralPM(1);
				}else if(proyectoActual.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO_ASIGNATURA)){
					proyectoActual.setAmbitoGeneralPM(2);
					proyectoActual.setDuracion(4);
				}
			
				InvestigadorProyecto director = new InvestigadorProyecto();
				Persona persona = (Persona) sesion.getAttribute("persona");
				InvestigadorInterno investigadorInterno = servicioPersona
						.obtenerInvestigadorInterno(new IdPersona(persona.getId().getDocumento(), persona.getId().getTipoDocumento()));
				director.setInvestigador(investigadorInterno);
				director.setDedicacionHorasSemana((short)0);
				director.setFuncion("Director");
				director.setProyecto(proyectoActual);
				TipoInvestigador ti = new TipoInvestigador();
				ti = (TipoInvestigador) servicioGeneral.obtenerObjeto( new TipoInvestigador(), "P");
				director.setTipo(ti);
				director.setValorPagar(0L);
				listaDirector.add(director);
				proyectoActual.setOpcionesPermisoMarco("     ");
				proyectoActual.adicionarInvestigadorProyecto(director);
			}else{
				opcion0 = proyectoActual.getOpcionMarco(0);
				opcion1 = proyectoActual.getOpcionMarco(1);
				opcion2 = proyectoActual.getOpcionMarco(2);
				opcion3 = proyectoActual.getOpcionMarco(3);
				opcion4 = proyectoActual.getOpcionMarco(4);
				opcion5 = proyectoActual.getOpcionMarco(5);
			}
	}
	
	private void cargarAmbito(){
		ambitoRecolecta = new SelectItem[2];
		ambitoRecolecta[0] = new SelectItem("1","Investigación cientifica");
		ambitoRecolecta[1] = new SelectItem("2","Recolección de especímenes para fines docentes.");
	}
	
	private void cargarAreaGeogragica(){
		if(proyectoActual.getAreaGeograficaPM() == null){
			proyectoActual.setAreaGeograficaPM(1);
		}
		areasGeograficas = new SelectItem[3];
		areasGeograficas[0] = new SelectItem("1","Nivel Nacional");
		areasGeograficas[1] = new SelectItem("2","Nivel Regional");
		areasGeograficas[2] = new SelectItem("3","Parques Nacionales Naturales de Colombia");
	}
	
	private void cargarActividades(){
		if(proyectoActual.getTipoActividadInvestigacionPM() == null){
			proyectoActual.setTipoActividadInvestigacionPM(1);
		}
		actividades = new SelectItem[4];
		actividades[0] = new SelectItem("1","Sistemática molecular");
		actividades[1] = new SelectItem("2","Ecología molecular");
		actividades[2] = new SelectItem("3","Evolución y biogeografía");
		actividades[3] = new SelectItem("4","Ninguno");
	}
	
	private void cargarColeccionesPermitidas() {
		listaColecciones = servicioGeneral
				.obtenerListaObjetos("Coleccion where estado not in ('B') ORDER BY nombre asc");

		if (listaColecciones != null
				&& listaColecciones.size() > 0) {
			listaColeccionesItem = new SelectItem[listaColecciones
					.size()];
			for (int i = 0; i < listaColecciones.size(); i++) {
				Coleccion col = (Coleccion) listaColecciones
						.get(i);
				listaColeccionesItem[i] = new SelectItem(col.getId(), col.getSede().getNombre()+" - "+col.getNombre());
			}
		}
	}
	
	public void setListaColecciones(List listaColecciones) {
		this.listaColecciones = listaColecciones;
	}

	public List getListaColecciones() {
		return listaColecciones;
	}

	public SelectItem[] getListaColeccionesItem() {
		return listaColeccionesItem;
	}

	public void setListaColeccionesItem(SelectItem[] listaColeccionesItem) {
		this.listaColeccionesItem = listaColeccionesItem;
	}
	

	public String siguiente() {
		boolean valida = true;
		if(proyectoActual.getAmbitoGeneralPM()==null || "".equals(proyectoActual.getAmbitoGeneralPM().toString().trim()) || proyectoActual.getAmbitoGeneralPM() == 0 ){
			mensajeError("Debe seleccionar el ámbito general.");
			valida = false;
		}
		if( opcion1 && (proyectoActual.getColeccionBiologicaPM()==null || "".equals(proyectoActual.getColeccionBiologicaPM().toString().trim()) || proyectoActual.getColeccionBiologicaPM() == 0 )){
			mensajeError("Debe seleccionar una colección biológica.");
			valida = false;
		}
		if(proyectoActual.getCateogoriasTaxonomicasPM() == null || "".equals(proyectoActual.getCateogoriasTaxonomicasPM().trim())){
			mensajeError("Debe ingresar información sobre el grupo taxonómico a ser recolectado.");
			valida = false;
		}
		
		if(!opcion1 && esCadenaVacia(proyectoActual.getNoColeccionBiologicaPM())){
			mensajeError("Debe indicar porqué no hay depósito en Colección Biológica.");
			valida = false;
		}
		
		if(!opcion1) {
			proyectoActual.setColeccionBiologicaPM(0);
		}else {
			proyectoActual.setNoColeccionBiologicaPM("");
		}
		
		if(valida){
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man.getItemProyecto() != null) {
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								link = lis[i].getOutcome();
								break;
							}
						}
			
						if (lis[i].getOutcome().equals("irInformacionEspecificaMarco")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}
					}
				}
			}
			System.out.println(link);
			
			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase())
						.intValue() + 1));
			}
	
			proyectoActual.setOpcionMarco(0, opcion0);
			proyectoActual.setOpcionMarco(1, opcion1);
			proyectoActual.setOpcionMarco(2, opcion2);
			proyectoActual.setOpcionMarco(3, opcion3);
			proyectoActual.setOpcionMarco(4, opcion4);
			proyectoActual.setOpcionMarco(5, opcion5);
			
			sesion.removeAttribute("manejadorMenuFormularios");
			EstadoProyecto estado = new EstadoProyecto();
			estado.setId("I");
			proyectoActual.setEstadoProyecto(estado);
			
			servicioGeneral.guardarObjeto(proyectoActual);
			
			proyectoActual  = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
					ProyectoDAOHibernate.TODO_POR_ID);
			
			sesion.setAttribute("proyecto", proyectoActual);
	
			return "irSubirArchivo";
		}else{
			return "";
		}
	}
	
	public String siguienteConsulta(){

		return "irSubirArchivo";
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}
	

	public String atras() {
		
		return "irFichaMinima";
	}
	
	public String salirGuardar() {

		return "misProyectos";
	}

	public void setColeccionSeleccionadaId(String coleccionSeleccionadaId) {
		this.coleccionSeleccionadaId = coleccionSeleccionadaId;
	}

	public String getColeccionSeleccionadaId() {
		return coleccionSeleccionadaId;
	}

	public void setOpcion1(Boolean opcion1) {
		this.opcion1 = opcion1;
	}

	public Boolean getOpcion1() {
		return opcion1;
	}

	public void setOpcion2(Boolean opcion2) {
		this.opcion2 = opcion2;
	}

	public Boolean getOpcion2() {
		return opcion2;
	}

	public void setOpcion3(Boolean opcion3) {
		this.opcion3 = opcion3;
	}

	public Boolean getOpcion3() {
		return opcion3;
	}

	public void setOpcion4(Boolean opcion4) {
		this.opcion4 = opcion4;
	}

	public Boolean getOpcion4() {
		return opcion4;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setAreaGeografica(String areaGeografica) {
		this.areaGeografica = areaGeografica;
	}

	public String getAreaGeografica() {
		return areaGeografica;
	}

	public void setCategoriaTaxonomicaMinima(String categoriaTaxonomicaMinima) {
		this.categoriaTaxonomicaMinima = categoriaTaxonomicaMinima;
	}

	public String getCategoriaTaxonomicaMinima() {
		return categoriaTaxonomicaMinima;
	}

	public void setOpcion5(Boolean opcion5) {
		this.opcion5 = opcion5;
	}

	public Boolean getOpcion5() {
		return opcion5;
	}

	public String getAmbitoId() {
		return ambitoId;
	}

	public void setAmbitoId(String ambitoId) {
		this.ambitoId = ambitoId;
	}

	public SelectItem[] getAmbitoRecolecta() {
		return ambitoRecolecta;
	}

	public void setAmbitoRecolecta(SelectItem[] ambitoRecolecta) {
		this.ambitoRecolecta = ambitoRecolecta;
	}

	public SelectItem[] getAreasGeograficas() {
		return areasGeograficas;
	}

	public void setAreasGeograficas(SelectItem[] areasGeograficas) {
		this.areasGeograficas = areasGeograficas;
	}

	public SelectItem[] getActividades() {
		return actividades;
	}

	public void setActividades(SelectItem[] actividades) {
		this.actividades = actividades;
	}

	public void setActividadesId(String actividadesId) {
		this.actividadesId = actividadesId;
	}

	public String getActividadesId() {
		return actividadesId;
	}
	private void cargarOpciones(){
		
		ArrayList<Boolean> opciones = proyectoActual.getOpcionesMarcoBoolean(6);
		if(opciones.size() == 6){ 
			opcion0 = (Boolean)opciones.get(0);
			opcion1 = (Boolean)opciones.get(1);
			opcion2 = (Boolean)opciones.get(2);
			opcion3 = (Boolean)opciones.get(3);
			opcion4 = (Boolean)opciones.get(4);
			opcion5 = (Boolean)opciones.get(5);
		}
		
	}

	public void setOpcion0(Boolean opcion0) {
		this.opcion0 = opcion0;
	}

	public Boolean getOpcion0() {
		return opcion0;
	}
	
}
