/**
* @author  Ing Hernán Darío Bernal Parra
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Bibliografia;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorBibliografia extends ManejadorProyecto {

    public static final String MANEJADOR_BIBLIOGRAFIA_SESSION = "manejadorBibliografia";
    
	private List listaBibliografia;
	private DataTable tablaBibliografia;
	private String mensajeErrorBibliografia = "";

	private String titulo1;
	private String titulo2;

	public ManejadorBibliografia() {
		super();

		titulo1 = "Proyecto:";
		titulo2 = "Búsqueda de Integrantes del Proyecto";

		idManejador = BIBLIOGRAFIA;
		listaBibliografia = new ArrayList();
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.BIBLIOGRAFIAS);
		listaBibliografia.addAll(proyectoActual.getBibliografias());

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			titulo1 = "Programa:";
			titulo2 = "Integrantes del programa";
		} else {
			titulo1 = "Proyecto:";
			titulo2 = "Integrantes del proyecto de investigación";
		}

	}

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
	}

	public String atras() {
		///////// MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			// NavigationMenuItem lis[] =
			// man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {
							return lis[i].getOutcome();
						}
					}

					if (lis[i].getOutcome().equals("irBibliografia")) {
						bandera = true;
					}

				}
			}
		}
		//////////////////

		if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
			return "irActividadPersona";
		}
		return "irActividades";
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar() {
		if (validarBibliografia()) {

			///// MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man!=null && man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irBibliografia")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}
					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) >= proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));
			}
			servicioProyecto.ingresarProyecto(proyectoActual);

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='160'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
				historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormualrioProyecto.setFormulario(formulario);
				historicoFormualrioProyecto.setProyecto(proyectoActual);
				historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
				//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
				//lo cual genera excepcion al guardar con rol Estudiante Lider
//          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			borrarManejadoresInsercionProyecto();
			return "misProyectos";
		}
		return "";
	}

	public String siguiente() {
		if (validarBibliografia()) {

			///// MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man != null && man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								// sesion.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irBibliografia")) {
							bandera = true;

						}
						if (lis[i].isRendered()) {
							pos++;
						}

					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));

			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='160'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
				historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormualrioProyecto.setFormulario(formulario);
				historicoFormualrioProyecto.setProyecto(proyectoActual);
				historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
				//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
				//lo cual genera excepcion al guardar con rol Estudiante Lider
//          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }
			
			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorFichaMinimaHome");
			sesion.removeAttribute("manejadorActividades");
			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorArchivos");

			///////// MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man != null) {
				if (man.getItemProyecto() != null) {
					// NavigationMenuItem lis[] =
					// man.getItemProyecto()[0].getNavigationMenuItems();
					MenuItem lis[] = man.getMenuItemArray();
					if (lis != null) {
						for (int i = 0; i < lis.length; i++) {
							if (bandera) {
								if (lis[i].isRendered()) {
									sesion.removeAttribute("manejadorMenuFormularios");
									// borrarManejadoresInsercionProyecto();
									return lis[i].getOutcome();
								}
							}

							if (lis[i].getOutcome().equals("irBibliografia")) {
								bandera = true;
							}

						}
					}
				}

			}
			//////////////////
			sesion.removeAttribute("manejadorMenuFormularios");
			return "irAreasTematicas";
		}
		return "";
	}

	// FUNCIONES ESPECIFICAS DE LA CLASE
	public void insertarReferencia() {
		Bibliografia b = new Bibliografia();
		b.setValor("");
		listaBibliografia.add(b);
		mensajeErrorBibliografia = "";
	}

	public void eliminarReferencia() {
		((Bibliografia) listaBibliografia.get(tablaBibliografia.getRowIndex())).setBorrable(true);
	}

	// VALIDADORES
	private boolean validarBibliografia() {
		List listaAuxiliar = new ArrayList();
		for (int i = 0; i < listaBibliografia.size(); i++) {
			Bibliografia oe = (Bibliografia) listaBibliografia.get(i);
			if (oe.isBorrable()) {
				listaAuxiliar.add(oe);
				proyectoActual.borrarBibliografia(oe);
			} else {
				proyectoActual.adicionarBibliografia(oe);
			}
		}
		listaBibliografia.removeAll(listaAuxiliar);
		if (listaBibliografia.isEmpty()) {
			mensajeErrorBibliografia = "No se encuentran referencias bibliográficas asociadas al proyecto";
			FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se encuentran referencias bibliográficas asociadas al proyecto", ""));
			return false;
		}
		mensajeErrorBibliografia = " ";
		return true;
	}

	// METODOS SET Y GET
	public String getMensajeErrorBibliografia() {
		return mensajeErrorBibliografia;
	}

	public void setMensajeErrorBibliografia(String mensajeErrorBibliografia) {
		this.mensajeErrorBibliografia = mensajeErrorBibliografia;
	}

	public List getListaBibliografia() {
		return listaBibliografia;
	}

	public void setListaBibliografia(List listaBibliografia) {
		this.listaBibliografia = listaBibliografia;
	}
	// public UIData getTablaBibliografia() {
	// return tablaBibliografia;
	// }
	// public void setTablaBibliografia(UIData tablaBibliografia) {
	// this.tablaBibliografia = tablaBibliografia;
	// }

	public String getTitulo1() {
		return titulo1;
	}

	public DataTable getTablaBibliografia() {
		return tablaBibliografia;
	}

	public void setTablaBibliografia(DataTable tablaBibliografia) {
		this.tablaBibliografia = tablaBibliografia;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}
	
	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

}
