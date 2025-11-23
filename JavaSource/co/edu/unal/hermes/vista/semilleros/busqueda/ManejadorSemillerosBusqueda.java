package co.edu.unal.hermes.vista.semilleros.busqueda;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroAreaOCDE;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemillerosBusqueda extends ManejadorBase {

	private static final long serialVersionUID = 1L;

	private Boolean error;
	private Semillero semilleroActual;
	private int opcion;
	private String idSemillero;
	private String areaOCDEP;
	private String subAreaOCDEP;
	private String ODSP;
	private String ODSS;

	public ManejadorSemillerosBusqueda() {
		error = true;
		opcion = 1;
		idSemillero = this.request.getParameter("id");
		cargarSemillero();
	}

	private void cargarSemillero() {
		sesion = request.getSession();
		this.error = false;
		if (StringUtils.isNotEmpty(idSemillero)) {
			try {
				semilleroActual = (Semillero) servicioGeneral.obtenerObjetoXID(Semillero.class, idSemillero).get(0);
				DominioDetalle dd = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"from DominioDetalle where identificador.tipo='" + semilleroActual.getAreaOCDEPrincipal() + "'")
						.get(0);
				areaOCDEP = dd.getDescripcion();
				subAreaOCDEP = servicioGeneral
						.obtenerObjetos(DominioDetalle.class, "from DominioDetalle where identificador.tipo='"
								+ semilleroActual.getSubAreaOCDEPrincipal() + "'")
						.get(0).getDescripcion();
				for (SemilleroAreaOCDE sao : semilleroActual.getListaAreasOCDESecundarias()) {
					if (sao.getAreaOCDE() == null) {
						sao.setAreaOCDE(servicioGeneral
								.obtenerObjetos(DominioDetalle.class, "from DominioDetalle where identificador.tipo='"
										+ sao.getSubAreaOCDE().getEstado() + "'")
								.get(0));
					}
				}
				setODSP(servicioGeneral
						.obtenerObjetos(DominioDetalle.class, "from DominioDetalle where identificador.tipo='"
								+ semilleroActual.getObjetivoDesarrolloSosteniblePrincipal() + "' and identificador.id='212'")
						.get(0).getDescripcion());
				setODSS(servicioGeneral
						.obtenerObjetos(DominioDetalle.class, "from DominioDetalle where identificador.tipo='"
								+ semilleroActual.getObjetivoDesarrolloSostenibleSecundario() + "' and identificador.id='212'")
						.get(0).getDescripcion());
			} catch (Exception i) {
				i.printStackTrace();
				error = false;
			}
			if (semilleroActual == null) {
				this.error = true;
			}
		}
		if (this.error) {
			this.opcion = 0;
		}
	}

	public String reporteSemilleroBusqueda() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idSemillero", idSemillero);
		r.setNombreReporte("/semilleros/reporteSemillero");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
		return "";
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public String getAreaOCDEP() {
		return areaOCDEP;
	}

	public void setAreaOCDEP(String areaOCDEP) {
		this.areaOCDEP = areaOCDEP;
	}

	public String getSubAreaOCDEP() {
		return subAreaOCDEP;
	}

	public void setSubAreaOCDEP(String subAreaOCDEP) {
		this.subAreaOCDEP = subAreaOCDEP;
	}

	public String getODSP() {
		return ODSP;
	}

	public void setODSP(String oDSP) {
		ODSP = oDSP;
	}

	public String getODSS() {
		return ODSS;
	}

	public void setODSS(String oDSS) {
		ODSS = oDSS;
	}

}
