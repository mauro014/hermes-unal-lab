package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Evaluador extends Investigador {
	
	private FuenteFinanciacion institucionLabora;
    private String tipo;
    private Investigador investigador;
	private String tesis;
	private FuenteFinanciacion institucionEstudio;
	private String areaCiencia;
	private String subAreaCiencia;
	private String cvlac;
	private String formacion;
	private String minciencias;
    private String areaExperticia;
    private Set<LineaInvestigacion> lineasInvestigacion = new HashSet<LineaInvestigacion>();
		
	private Set proyectosEvaluador = new HashSet();

    public String getAreaExperticia() {
        return areaExperticia;
    }
    public void setAreaExperticia(String areaExperticia) {
        this.areaExperticia = areaExperticia;
    }
    
    public Set getProyectosEvaluador() {
        return proyectosEvaluador;
    }
    public void setProyectosEvaluador(Set proyectosEvaluador) {
        this.proyectosEvaluador = proyectosEvaluador;
    } 
    
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Investigador getInvestigador() {
		return investigador;
	}
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}
	public String getTesis() {
		return tesis;
	}
	public void setTesis(String tesis) {
		this.tesis = tesis;
	}
	public FuenteFinanciacion getInstitucionEstudio() {
		return institucionEstudio;
	}
	public void setInstitucionEstudio(FuenteFinanciacion institucionEstudio) {
		this.institucionEstudio = institucionEstudio;
	}
	public String getAreaCiencia() {
		return areaCiencia;
	}
	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}
	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}
	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}
	public String getCvlac() {
		return cvlac;
	}
	public void setCvlac(String cvlac) {
		this.cvlac = cvlac;
	}
	public String getFormacion() {
		return formacion;
	}
	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}
	public String getMinciencias() {
		return minciencias;
	}
	public void setMinciencias(String minciencias) {
		this.minciencias = minciencias;
	}
	public FuenteFinanciacion getInstitucionLabora() {
		return institucionLabora;
	}
	public void setInstitucionLabora(FuenteFinanciacion institucionLabora) {
		this.institucionLabora = institucionLabora;
	}
	
	public void adicionarLineaInvestigacion(LineaInvestigacion linea) {
		boolean encontro = false;
		Iterator<LineaInvestigacion> it = lineasInvestigacion.iterator();
		while (it.hasNext()) {
			LineaInvestigacion l = (LineaInvestigacion) it.next();
			if (l.getId().equals(linea.getId())) {
				encontro = true;
				break;
			}
		}
		if (!encontro) {
			lineasInvestigacion.add(linea);
		}
	}
	
	public void borrarLineaInvestigacion(LineaInvestigacion linea) {
		lineasInvestigacion.remove(linea);
	}
	public Set<LineaInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}
	public void setLineasInvestigacion(Set<LineaInvestigacion> lineas) {
		this.lineasInvestigacion = lineas;
	}
	
	public List<LineaInvestigacion> getListaLineasInvestigacion() {
		ArrayList<LineaInvestigacion> lista = new ArrayList<LineaInvestigacion>();
		lista.addAll(this.lineasInvestigacion);
		return lista;
	}
}
