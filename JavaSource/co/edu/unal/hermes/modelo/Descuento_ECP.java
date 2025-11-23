package co.edu.unal.hermes.modelo;

public class Descuento_ECP
{
	Long id_descuento;
	String descuento_desc;
	int descuento_porc;
	
	public Long getId_descuento() {
		return id_descuento;
	}
	public void setId_descuento(Long id_descuento) {
		this.id_descuento = id_descuento;
	}
	public String getDescuento_desc() {
		return descuento_desc;
	}
	public void setDescuento_desc(String descuento_desc) {
		this.descuento_desc = descuento_desc;
	}
	public int getDescuento_porc() {
		return descuento_porc;
	}
	public void setDescuento_porc(int descuento_porc) {
		this.descuento_porc = descuento_porc;
	}
	
//	public Object clone() throws CloneNotSupportedException {
//		return super.clone();
//	    }
//
//	public boolean equals(Object o) 
//	{
//		if (!(o instanceof Descuento_ECP)) {
//		    return false;
//		}
//		Descuento_ECP descuentoECP = (Descuento_ECP) o;
//		if (descuentoECP == null || descuentoECP.getId_descuento() == null || this.getId_descuento() == null) {
//		    return false;
//		}
//		return descuentoECP.getId_descuento().equals(this.getId_descuento());
//	}
	
	
	
}