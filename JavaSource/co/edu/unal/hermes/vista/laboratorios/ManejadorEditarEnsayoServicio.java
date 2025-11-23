package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipoInterfazAlerta;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.servicios.imp.ServicioCorreo;
import co.edu.unal.hermes.seguridad.autenticacion.Usuario;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorEditarEnsayoServicio extends ManejadorBase {

	public LaboratorioDetalleEquipos equipo;
	private Boolean soloLectura;
	
	private LaboratorioDetalleEnsayosServicios ensayoServicio;
	
	private SelectItem[] unidadesTiempoItem;
	private String unidadTiempoTotalEstimado;
	private List<SelectItem> equiposUsadosItem;
	private String[] equiposSeleccionadosItem;
	private SelectItem[] tiposNormaLista;
	private String tipoNormaSeleccionado;
	
	private SelectItem[] tipoEnsayoItem;

	public ManejadorEditarEnsayoServicio() {
		System.out.println("ManejadorEditarEnsayoServicio:");
		limpiarSesion();
		ensayoServicio = (LaboratorioDetalleEnsayosServicios) sesion.getAttribute("ensayoServicioSeleccionado");
		
		equiposSeleccionadosItem = null;
		unidadTiempoTotalEstimado = Tipos.UNIDAD_TIEMPO_MINUTOS.toString();
		
		cargarListas();
		cargarValoresListas();
	}
	
	public void cargarListas() {
		
		// Se buscan los equipos en uso por ese laboratorio:
		String hql2 = "FROM LaboratorioDetalleEquipos WHERE enUso = 1 AND dadoDeBaja = 0 AND laboratorio = "
				+ ensayoServicio.getLaboratorio().getId() + " ORDER BY id";
		System.out.println("Búsqueda equipos: " + hql2);
		List<LaboratorioDetalleEquipos> listaEquiposLaboratorio = new ArrayList<LaboratorioDetalleEquipos>();
		listaEquiposLaboratorio = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql2);
		
		equiposUsadosItem = new Vector<SelectItem>();
		for (LaboratorioDetalleEquipos g : listaEquiposLaboratorio) {
			System.out.println("equiposUsadosItem: " + g.getId());
			equiposUsadosItem.add(new SelectItem(g.getId().toString(), g.getPlaca() + " " + g.getEquipo()));
		}
		
		unidadesTiempoItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.UNIDADES_TIEMPO);
		tiposNormaLista = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO);
		tipoEnsayoItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_TIPO_ENSAYO);
	}
	
	public void cargarValoresListas() {
	
		Set<LaboratorioDetalleEquipos> equipos = ensayoServicio.getEquipos();
		List<String> listaIds = new ArrayList<String>();
		
		for (LaboratorioDetalleEquipos e : equipos)
			listaIds.add(e.getId().toString());
		
		equiposSeleccionadosItem = new String[listaIds.size()];
		listaIds.toArray(equiposSeleccionadosItem);
	}
	
	public Boolean validar() {
		Boolean validar = true;	
		
		// Validar
		if (esCadenaVacia(ensayoServicio.getNombre().toString()) || ensayoServicio.getNombre().equals("")) {
			validar = false;
			mensajeError("El NOMBRE es un campo obligatorio");
		}
		
		if(esNulo(ensayoServicio.getTipoEnsayo()) || ensayoServicio.getTipoEnsayo().getId().equals(0L)){
			validar = false;
			mensajeError("El TIPO es un campo obligatorio");
		}
		
		return validar;
	}

	public String guardarEnsayoServicio() {
		if (validar()) 
		{
			// equipos asociados al proyecto:
			Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
			for (String e : equiposSeleccionadosItem) {
				LaboratorioDetalleEquipos detalleEquipo = servicioGeneral
						.obtenerObjetos(
								LaboratorioDetalleEquipos.class,
								"FROM LaboratorioDetalleEquipos WHERE id = "+ e).get(0);
				equipos.add(detalleEquipo);
			}
			ensayoServicio.setEquipos(equipos);
			
			if(!ensayoServicio.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_otros))
				ensayoServicio.setTipoEnsayoOtros(null);
			
			try
			{
				servicioGeneral.guardarObjeto(ensayoServicio);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return volverLaboratorio();
		
		} else {
			return "";
		}
	}
	
	public String volverLaboratorio() {
		System.out.println("volverLaboratorio:");
		limpiarSesion();
		return "laboratorioEnsayosServicios";
	}
	
	public void limpiarSesion() {
		System.out.println("limpiarSesion:");
		sesion.removeAttribute("ManejadorEditarEnsayoServicio");
//		sesion.removeAttribute("ManejadorLaboratoriosEnsayosServicios");
	}
	
	public void enviarNotificacionCreacionEquipo()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_ASOCIADO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(equipo.getLaboratorio().getId());
		String rol = null;
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		
		List<PersonaRol> listaPr = servicioGeneral.obtenerPersonaRolXIdPersonaLaboratorios(ii.getId().getTipoDocumento(),ii.getId().getDocumento());
		if(listaPr != null)
			rol = servicioPersona.obtenerRol(listaPr.get(0).getNombre()).getNombre();

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipo.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", equipo.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_COORDINADOR>>", coorinador.getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipo.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipo.getPlaca());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<NOMBRE_USUARIO_SESION>>", personaActual.getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rol);
		cuerpo = cuerpo.replaceAll("<<NOMBRE_FACULTAD>>", ii.getDependencia().getFacultad().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", ii.getDependencia().getFacultad().getSede().getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);

	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	public LaboratorioDetalleEnsayosServicios getEnsayoServicio() {
		return ensayoServicio;
	}

	public void setEnsayoServicio(LaboratorioDetalleEnsayosServicios ensayoServicio) {
		this.ensayoServicio = ensayoServicio;
	}

	public SelectItem[] getUnidadesTiempoItem() {
		return unidadesTiempoItem;
	}

	public void setUnidadesTiempoItem(SelectItem[] unidadesTiempoItem) {
		this.unidadesTiempoItem = unidadesTiempoItem;
	}

	public List<SelectItem> getEquiposUsadosItem() {
		return equiposUsadosItem;
	}

	public void setEquiposUsadosItem(List<SelectItem> equiposUsadosItem) {
		this.equiposUsadosItem = equiposUsadosItem;
	}

	public String[] getEquiposSeleccionadosItem() {
		return equiposSeleccionadosItem;
	}

	public void setEquiposSeleccionadosItem(String[] equiposSeleccionadosItem) {
		this.equiposSeleccionadosItem = equiposSeleccionadosItem;
	}

	public SelectItem[] getTiposNormaLista() {
		return tiposNormaLista;
	}

	public void setTiposNormaLista(SelectItem[] tiposNormaLista) {
		this.tiposNormaLista = tiposNormaLista;
	}

	public String getUnidadTiempoTotalEstimado() {
		return unidadTiempoTotalEstimado;
	}

	public void setUnidadTiempoTotalEstimado(String unidadTiempoTotalEstimado) {
		this.unidadTiempoTotalEstimado = unidadTiempoTotalEstimado;
	}

	public String getTipoNormaSeleccionado() {
		return tipoNormaSeleccionado;
	}

	public void setTipoNormaSeleccionado(String tipoNormaSeleccionado) {
		this.tipoNormaSeleccionado = tipoNormaSeleccionado;
	}

	public SelectItem[] getTipoEnsayoItem() {
		return tipoEnsayoItem;
	}

	public void setTipoEnsayoItem(SelectItem[] tipoEnsayoItem) {
		this.tipoEnsayoItem = tipoEnsayoItem;
	}

//	public SelectItem[] getAcreditadoCertificadoItem() {
//		return acreditadoCertificadoItem;
//	}
//
//	public void setAcreditadoCertificadoItem(SelectItem[] acreditadoCertificadoItem) {
//		this.acreditadoCertificadoItem = acreditadoCertificadoItem;
//	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}
}