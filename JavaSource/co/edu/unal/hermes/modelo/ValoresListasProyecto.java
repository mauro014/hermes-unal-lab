package co.edu.unal.hermes.modelo;


public class ValoresListasProyecto implements Cloneable {

    private Long id;
    private String descripcion;
    private String tipo;
    private Proyecto proyecto; 
    private String valor;
    private String nombreValor;
    private String valorDos;
    private String descripcionDos;
    private String valorTres;
    private String descripcionTres;
    private String valorCuatro;
    private String descripcionCuatro;
    
	public ValoresListasProyecto()
	{
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the descripcion
	 */
	public String getDescripcion()
	{
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo()
	{
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
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
	 * @return the valor
	 */
	public String getValor()
	{
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor)
	{
		this.valor = valor;
	}

	public boolean equals(Object o) {
        boolean tieneProyecto;
        boolean tieneValorUno;
        boolean tieneValorDos;
        boolean tieneValorTres;
		if (!(o instanceof ValoresListasProyecto)) {
            return false;
        }
		
        ValoresListasProyecto vlp = (ValoresListasProyecto) o;
        
        if(null != vlp.getProyecto() && null != this.getProyecto()){
       	 if(null != vlp.getProyecto().getId() && null != this.getProyecto().getId()){
            	if(vlp.getProyecto().getId().equals(this.getProyecto().getId())){
            		tieneProyecto = true;
            	}else{
            		tieneProyecto = false;
            	}
            }else{
            	tieneProyecto = true;
            }
       }else{
       	tieneProyecto = true;
       }
        
        if(vlp.getValor() != null && this.getValor() != null){
        	if(vlp.getValor().equals(this.getValor())){
        		tieneValorUno = true;
        	}else{
        		tieneValorUno = false;
        	}
        }else{
        	tieneValorUno = true;
        }
        
        if(vlp.getValorDos() != null && this.getValorDos() != null){
        	if(vlp.getValorDos().equals(this.getValorDos())){
        		tieneValorDos = true;
        	}else{
        		tieneValorDos = false;
        	}
        }else{
        	tieneValorDos = true;
        }
        
        if(vlp.getValorTres() != null && this.getValorTres() != null){
        	if(vlp.getValorTres().equals(this.getValorTres())){
        		tieneValorTres = true;
        	}else{
        		tieneValorTres = false;
        	}
        }else{
        	tieneValorTres = true;
        }
        	
        return vlp.getTipo().equals(this.getTipo()) && tieneProyecto && tieneValorUno && tieneValorDos && tieneValorTres;
    }

	public String getNombreValor() {
		return nombreValor;
	}

	public void setNombreValor(String nombreValor) {
		this.nombreValor = nombreValor;
	}

	public String getValorDos() {
		return valorDos;
	}

	public void setValorDos(String valorDos) {
		this.valorDos = valorDos;
	}

	public String getDescripcionDos() {
		return descripcionDos;
	}

	public void setDescripcionDos(String descripcionDos) {
		this.descripcionDos = descripcionDos;
	}

	public String getValorTres() {
		return valorTres;
	}

	public void setValorTres(String valorTres) {
		this.valorTres = valorTres;
	}

	public String getDescripcionTres() {
		return descripcionTres;
	}

	public void setDescripcionTres(String descripcionTres) {
		this.descripcionTres = descripcionTres;
	}

	public String getValorCuatro() {
		return valorCuatro;
	}

	public void setValorCuatro(String valorCuatro) {
		this.valorCuatro = valorCuatro;
	}

	public String getDescripcionCuatro() {
		return descripcionCuatro;
	}

	public void setDescripcionCuatro(String descripcionCuatro) {
		this.descripcionCuatro = descripcionCuatro;
	}
   
}
