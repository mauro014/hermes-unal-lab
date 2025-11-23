package co.edu.unal.hermes.modelo;

public class TipoFinanciacion{
    
	public static String SI="S";
	public static String NO="N";
	public static String ELEGIBLE="E";
    
	private String id;
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
