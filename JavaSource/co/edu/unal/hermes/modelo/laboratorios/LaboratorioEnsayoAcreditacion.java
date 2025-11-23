/**
} * @author dgbenitezc
 */

package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Proyecto;

public class LaboratorioEnsayoAcreditacion {

	private Long id;
	private Proyecto proyecto;
	private String nombreEnsayo;
	private String areaAcreditacion;
	private String documentoReferencia;

	public LaboratorioEnsayoAcreditacion() {
	}


	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the proyecto
	 */
	public Proyecto getProyecto()
	{
		return proyecto;
	}

	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}

	/**
	 * @return the nombreEnsayo
	 */
	public String getNombreEnsayo()
	{
		return nombreEnsayo;
	}

	/**
	 * @param nombreEnsayo the nombreEnsayo to set
	 */
	public void setNombreEnsayo(String nombreEnsayo)
	{
		this.nombreEnsayo = nombreEnsayo;
	}

	/**
	 * @return the areaAcreditacion
	 */
	public String getAreaAcreditacion()
	{
		return areaAcreditacion;
	}

	/**
	 * @param areaAcreditacion the areaAcreditacion to set
	 */
	public void setAreaAcreditacion(String areaAcreditacion)
	{
		this.areaAcreditacion = areaAcreditacion;
	}

	/**
	 * @return the documentoReferencia
	 */
	public String getDocumentoReferencia()
	{
		return documentoReferencia;
	}

	/**
	 * @param documentoReferencia the documentoReferencia to set
	 */
	public void setDocumentoReferencia(String documentoReferencia)
	{
		this.documentoReferencia = documentoReferencia;
	}



}
