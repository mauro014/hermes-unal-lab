package co.edu.unal.hermes.modelo;

public class TipoArchivo {

    public static Short tipoProyecto = new Short((short) 1);
    
    public static final Short CONCEPTO_DRE = 174;
	public static final Short FORMATO_DRE_SPANISH = 172;
	public static final Short FORMATO_DRE_ENGLISH = 173;

    private Short id;
    private String nombre;
    private String parametro; // para ser empleado en el conjunto de archivos que se requiera
    private String estado;
    private boolean obligatorio;
    private String convocatoriaExterna;

    public TipoArchivo() {
        
    }
    
    public TipoArchivo(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getConvocatoriaExterna() {
		return convocatoriaExterna;
	}

	public void setConvocatoriaExterna(String convocatoriaExterna) {
		this.convocatoriaExterna = convocatoriaExterna;
	}

}
