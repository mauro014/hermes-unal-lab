/*
 * Created on 27-ene-2006
 */
package co.edu.unal.hermes.modelo;


/**
 * @author jpduqueg
 */
public class ProyectoProducto {
    
    private Long id;    
    private Proyecto proyecto;
    private ProductoTipo producto;
    private String completado;
    private int cantidad;
    private String descripcion; 
    private String motivoNoEntrega; 
    
    //Este atributo no se mapea en la base de datos
    //Sirve para identificar el producto a asociar cuando
    //la cantidad es mayor a 1
    private int index;
    
    private boolean entregado;
    
	/**
	 * Constructo tradicional
	 */
	public ProyectoProducto(){
		
	}
	
	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public ProyectoProducto(Long pId){
		this.id = pId;
	}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }    
      	
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public ProductoTipo getProducto() {
        return producto;
    }
    public void setProducto(ProductoTipo producto) {
        this.producto = producto;
    }
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean getEntregado() 
	{
		return entregado;
	}

	public void setEntregado(boolean entregado) 
	{
		this.entregado = entregado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    /**
     * @return the completado
     */
    public String getCompletado() {
        return completado;
    }

    /**
     * @param completado the completado to set
     */
    public void setCompletado(String completado) {
        this.completado = completado;
    }

    /**
     * @return the motivoNoEntrega
     */
    public String getMotivoNoEntrega() {
        return motivoNoEntrega;
    }

    /**
     * @param motivoNoEntrega the motivoNoEntrega to set
     */
    public void setMotivoNoEntrega(String motivoNoEntrega) {
        this.motivoNoEntrega = motivoNoEntrega;
    }
	
	
}
