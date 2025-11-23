package co.edu.unal.hermes.modelo.laboratorios;

public class LaboratorioVista
{
	
	private String nombreLaboratorio;
	private String coordiadorLaboratorio;
	private Long idLaboratorio;
	private String emailLaboratorio;
	
	
	public LaboratorioVista()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the nombreLaboratorio
	 */
	public String getNombreLaboratorio()
	{
		return nombreLaboratorio;
	}
	/**
	 * @param nombreLaboratorio the nombreLaboratorio to set
	 */
	public void setNombreLaboratorio(String nombreLaboratorio)
	{
		this.nombreLaboratorio = nombreLaboratorio;
	}
	/**
	 * @return the coordiadorLaboratorio
	 */
	public String getCoordiadorLaboratorio()
	{
		return coordiadorLaboratorio;
	}
	/**
	 * @param coordiadorLaboratorio the coordiadorLaboratorio to set
	 */
	public void setCoordiadorLaboratorio(String coordiadorLaboratorio)
	{
		this.coordiadorLaboratorio = coordiadorLaboratorio;
	}
	/**
	 * @return the idLaboratorio
	 */
	public Long getIdLaboratorio()
	{
		return idLaboratorio;
	}
	/**
	 * @param idLaboratorio the idLaboratorio to set
	 */
	public void setIdLaboratorio(Long idLaboratorio)
	{
		this.idLaboratorio = idLaboratorio;
	}
	/**
	 * @return the emailLaboratorio
	 */
	public String getEmailLaboratorio()
	{
		return emailLaboratorio;
	}
	/**
	 * @param emailLaboratorio the emailLaboratorio to set
	 */
	public void setEmailLaboratorio(String emailLaboratorio)
	{
		this.emailLaboratorio = emailLaboratorio;
	}

}
