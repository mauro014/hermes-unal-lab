package co.edu.unal.hermes.modelo;

public class Empresa {
    
	private Long id;
	private String nombre;
	private String representanteLegal;
	private String tipoEmpresa;
	private String ubicacionCadenaProductiva;
	private String direccion;
	private String domicilio;
	private String nit;
	private String email;
	private String paginaWeb;
	private String telefono;
	
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase().trim();
    }
    public String getRepresentanteLegal() {
        return representanteLegal;
    }
    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }
    public String getTipoEmpresa() {
        return tipoEmpresa;
    }
    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }
    public String getUbicacionCadenaProductiva() {
        return ubicacionCadenaProductiva;
    }
    public void setUbicacionCadenaProductiva(String ubicacionCadenaProductiva) {
        this.ubicacionCadenaProductiva = ubicacionCadenaProductiva;
    }
    public boolean equals(Object e)
    {
        if(!(e instanceof Empresa))
        {
            return false;
        }
        if(e==null)
        {
            return false;
        }
        if(this.getId()==null)
        {
            return false;
        }
        Empresa emp=(Empresa)e;
        return emp.getId().equals(this.getId());
    }
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}
	/**
	 * @param paginaWeb the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
    
}
