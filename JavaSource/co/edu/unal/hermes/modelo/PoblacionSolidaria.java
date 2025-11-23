package co.edu.unal.hermes.modelo;

/**
 * The Class PoblacionSolidaria.
 */
public class PoblacionSolidaria implements java.io.Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3429283617499779771L;

    /** The id. */
    Long id;

    /** The aval. */
    Aval aval;

    /** The poblacion. */
    String poblacion = "";

    /** The poblaciontxt. */
    String poblaciontxt = "";

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this.poblacion.equals(((PoblacionSolidaria) obj).poblacion)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the poblacion.
     *
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Sets the poblacion.
     *
     * @param poblacion
     *            the new poblacion
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Gets the poblaciontxt.
     *
     * @return the poblaciontxt
     */
    public String getPoblaciontxt() {
        return poblaciontxt;
    }

    /**
     * Sets the poblaciontxt.
     *
     * @param poblaciontxt
     *            the new poblaciontxt
     */
    public void setPoblaciontxt(String poblaciontxt) {
        this.poblaciontxt = poblaciontxt;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the aval.
     *
     * @return the aval
     */
    public Aval getAval() {
        return aval;
    }

    /**
     * Sets the aval.
     *
     * @param aval
     *            the new aval
     */
    public void setAval(Aval aval) {
        this.aval = aval;
    }

}
