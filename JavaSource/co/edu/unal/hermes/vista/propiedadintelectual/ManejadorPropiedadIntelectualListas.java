package co.edu.unal.hermes.vista.propiedadintelectual;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;

public class ManejadorPropiedadIntelectualListas extends ManejadorBasePropiedadIntelectual {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tamanoLista;
	private final static String[] reportes;
	private int reporteSeleccionado;
	private InvestigadorInterno personaTramita;

	/*
	 * Variables temporales para parametros de reporte
	 */
	private String subTipoPropiedad;

	static {
		reportes = new String[4];
		reportes[0] = "Registros propiedad intelectual con estado";
		reportes[1] = "Registros propiedad intelectual por categoría específica";
		reportes[2] = "Registros propiedad intelectual por persona";
		reportes[3] = "Registros propiedad intelectual con entidad específica";
	}

	public ManejadorPropiedadIntelectualListas() {
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
	}

	public List<PropiedadIntelectual> getListaPropiedades() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/propiedadIntelectual/propiedadIntelectual.xhtml") != -1) {
			listaPropiedades = servicioPropiedadIntelectual.obtenerPropiedadesRegistradasPersona(cargarPersonaActual());
		} else if (sesion.getAttribute(ROL_INGRESO_PI) != null && "N".equals(sesion.getAttribute(ROL_INGRESO_PI))) {
			listaPropiedades = servicioPropiedadIntelectual.obtenerPropiedadesPendientesNacional();
		} else if (sesion.getAttribute(ROL_INGRESO_PI) != null && "S".equals(sesion.getAttribute(ROL_INGRESO_PI))) {
			personaTramita = servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId());
			listaPropiedades = servicioPropiedadIntelectual
					.obtenerPropiedadesPendientesSede(personaTramita.getDependencia2().getSede().getId());
		}
		for (PropiedadIntelectual p : listaPropiedades) {
			p.setFechaRegistroConFormato(customFormatDate(p.getFechaRegistro()));
		}
		return listaPropiedades;
	}

	/**
	 * metodos para menu de propiedad intelectual
	 * 
	 * @return
	 */

	public String registrarPropiedad() {
		eliminarSesionPI();
		sesion.setAttribute(TIPO_ACCESO, ManejadorBasePropiedadIntelectual.TIPO_NUEVO);
		return "registroPropiedadIntelectual";
	}

	public String verPropiedadRegistrada() {
		eliminarSesionPI();
		return "irPropiedadIntelectual";
	}

	public String verPropiedadTramitar() {
		eliminarSesionPI();
		return "dinipi";
	}

	public String tramitarPropiedad() {
		eliminarSesionPI();
		sesion.setAttribute(ID_PROPIEDAD_INTELECTUAL, idPropiedad);
		return "tramitarPropiedadIntelectual";
	}

	public String consultarRegistroPropiedad() {
		eliminarSesionPI();
		return "consultarRegistroPropiedad";
	}

	public String reportesPropiedadIntelectual() {
		eliminarSesionPI();
		return "reportesPropiedadIntelectual";
	}

	public int getTamanoLista() {
		if (listaPropiedades != null) {
			return listaPropiedades.size();
		}
		return tamanoLista;
	}

	public void setTamanoLista(int tamanoLista) {
		this.tamanoLista = tamanoLista;
	}

	public List<Object[]> getReportesHabilitados() {
		List<Object[]> reportesHabilitados;
		int cantidad = reportes.length;
		reportesHabilitados = new ArrayList<Object[]>();
		for (int i = 0; i < cantidad; i++) {
			Object[] r1 = new Object[2];
			r1[0] = i;
			r1[1] = reportes[i];
			reportesHabilitados.add(r1);
		}
		return reportesHabilitados;
	}

	private String subTiposPIReporteGral() {
		String subTipos = "";
		List<SubTipoPropiedadIntelectual> lista = servicioPropiedadIntelectual.obtenerSubTiposPropiedadIntelectual();
		if (!esListaVacia(lista)) {
			for (SubTipoPropiedadIntelectual sub : lista) {
				subTipos = subTipos + " " + sub.getId();
			}
		}
		return subTipos;
	}

	public String generarReporte() {
		boolean generar = true;
		ReporteBirt r = new ReporteBirt();
		r.setNombreReporte("/propiedad-intelectual/registrosPropiedadIntelectual");
		r.adicionarParametro("subtipo", "0");
		r.adicionarParametro("td", "-");
		r.adicionarParametro("d", "-");
		r.adicionarParametro("entidad", "-");

		switch (reporteSeleccionado) {
		case 1:
			if (esCadenaVacia(subTipoPropiedad)) {
				mensajeError("Debe seleccionar una parámetro para el reporte");
				generar = false;
			} else {
				r.adicionarParametro("subtipo", subTipoPropiedad);
			}
			break;
		case 2:
			if (esCadenaVacia(documento) || esCadenaVacia(tipoDocumento)) {
				mensajeError("Debe seleccionar el tipo de documento y diligenciar el documento de la persona");
				generar = false;
			} else {
				r.adicionarParametro("td", tipoDocumento);
				r.adicionarParametro("d", documento.trim());
			}
			break;
		case 3:
			if (esCadenaVacia(entidadExterna)) {
				mensajeError("Debe seleccionar la entidad cotitular para el reporte");
				generar = false;
			} else {
				r.adicionarParametro("entidad", entidadExterna);
			}
			break;
		default:
			r.adicionarParametro("subtipo", subTiposPIReporteGral());
			break;
		}

		if (generar) {
			if (sesion.getAttribute(ROL_INGRESO_PI) != null && "S".equals(sesion.getAttribute(ROL_INGRESO_PI))) {
				personaTramita = servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId());
				r.adicionarParametro("sede", personaTramita.getDependencia2().getSede().getId().toString());
			} else {
				r.adicionarParametro("sede", "1 2 3 4 5 6 7 8 9 0");
			}

			r.setFormato(ReporteBirt.FORMATO_XLS);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			r.run(context);
		}
		return "";
	}

	public void imprimirPropiedad() {
		generarPDFPropiedad(idPropiedad.toString());
	}

	public List<Object[]> getIndicadoresRapidos() {
		return servicioPropiedadIntelectual.obtenerIndicadoresRapidos();
	}

	public int getReporteSeleccionado() {
		return reporteSeleccionado;
	}

	public void setReporteSeleccionado(int reporteSeleccionado) {
		this.reporteSeleccionado = reporteSeleccionado;
	}

	public InvestigadorInterno getPersonaTramita() {
		return personaTramita;
	}

	public void setPersonaTramita(InvestigadorInterno personaTramita) {
		this.personaTramita = personaTramita;
	}

	public String getSubTipoPropiedad() {
		return subTipoPropiedad;
	}

	public void setSubTipoPropiedad(String subTipoPropiedad) {
		this.subTipoPropiedad = subTipoPropiedad;
	}

	// Inicio Requerimiento #2353
	public static String datePattern() {
		return "dd/MM/yyyy";
	}

	public static String customFormatDate(Date date) {
		if (date != null) {
			DateFormat format = new SimpleDateFormat(datePattern());
			return format.format(date);
		}
		return "";
	}
	// Fin Requerimiento #2353

}