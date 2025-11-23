/*
 * Created on 27-ene-2006
 */
package co.edu.unal.hermes.modelo;

/**
 * @author jpduqueg
 */
public class MovilidadRequisito {
    
    private Long id;    
    private Long idMovilidad;
    private TipoRequisito requisito;
    private String cumplido;
    private String comentario;
    private String responsable_id;
    private String responsable_tdo_id;
    
    //Este atributo no se mapea en la base de datos
    //Sirve para identificar el producto a asociar cuando
    //la cantidad es mayor a 1
    private int index;
    
    private boolean entregado;
    
	
    public MovilidadRequisito(){
    }
    
	public MovilidadRequisito(Long pId){
		this.id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoRequisito getRequisito() {
		return requisito;
	}

	public void setRequisito(TipoRequisito requisito) {
		this.requisito = requisito;
	}

	public String getCumplido() {
		return cumplido;
	}

	public void setCumplido(String cumplido) {
		this.cumplido = cumplido;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void setCumplidoCheckbox(boolean cumplidoCheckbox) {
		if(cumplidoCheckbox == true){
			cumplido = "S";
		}
		else cumplido = "N";
	}

	public boolean isCumplidoCheckbox() {
    	if(cumplido != null && cumplido.equals("S")){
    		return true;
    	}
    	else return false;
	}

	public void setResponsable_id(String responsable_id) {
		this.responsable_id = responsable_id;
	}

	public String getResponsable_id() {
		return responsable_id;
	}

	public void setResponsable_tdo_id(String responsable_tdo_id) {
		this.responsable_tdo_id = responsable_tdo_id;
	}

	public String getResponsable_tdo_id() {
		return responsable_tdo_id;
	}

	public Long getIdMovilidad() {
		return idMovilidad;
	}

	public void setIdMovilidad(Long idMovilidad) {
		this.idMovilidad = idMovilidad;
	}	
}
