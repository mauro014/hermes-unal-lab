/********************************************************************************
Autor 		: Ing. Wilver Alexander Martínez Martínez - wam² - UN.
Clase    	: co.edu.unal.hermes.modelo.TipoInforme
Objetivo 	: Clase POJO para la tabla HER_TIPO_INFORME en la cual se encuentran  
			  los tipos de informes para un proyecto de investigación.
Creación	: Abril 07 de 2010
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class TipoMovilidad.
 */
public class TipoMovilidad implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7518444645372773213L;

    /** The Constant RAIZ. */
    public static final String RAIZ = "0";

    /** The Constant HER_MOVILIDAD_VISITANTES_EXT. */
    public static final String HER_MOVILIDAD_VISITANTES_EXT = "HER_MOVILIDAD_VISITANTES_EXT";

    /** The Constant HER_MOVILIDAD_VISITANTES_ART. */
    public static final String HER_MOVILIDAD_VISITANTES_ART = "HER_MOVILIDAD_VISITANTES_ART";

    /** The Constant HER_MOVILIDAD_DOCENTES_EVENTOS. */
    public static final String HER_MOVILIDAD_DOCENTES_EVENTOS = "HER_MOVILIDAD_DOCENTES_EVENTOS";

    /** The Constant HER_MOVILIDAD_DOCENTES_ART. */
    public static final String HER_MOVILIDAD_DOCENTES_ART = "HER_MOVILIDAD_DOCENTES_ART";

    /** The Constant HER_MOVILIDAD_ESTUDIANTE_POS. */
    public static final String HER_MOVILIDAD_ESTUDIANTE_POS = "HER_MOVILIDAD_ESTUDIANTE_POS";

    /** The Constant HER_MOVILIDAD_ESTUDIANTES_ART. */
    public static final String HER_MOVILIDAD_ESTUDIANTES_ART = "HER_MOVILIDAD_ESTUDIANTES_ART";

    /** The id. */
    private String id;

    /** The nombre. */
    private String nombre;

    /** The tabla. */
    private String tabla;

    /** The nombre. */
    private String nombreVista;

    /** The es pasantia. */
    private Boolean esPasantia;

    /**
     * Instantiates a new tipo movilidad.
     */
    public TipoMovilidad() {
    }

    /**
     * Gets the nombre.
     *
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre
     *            The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the tabla.
     *
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * Sets the tabla.
     *
     * @param tabla
     *            the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * Gets the nombre vista.
     *
     * @return the nombreVista
     */
    public String getNombreVista() {
        return nombreVista;
    }

    /**
     * Sets the nombre vista.
     *
     * @param nombreVista
     *            the nombreVista to set
     */
    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }

    /**
     * Gets the es pasantia.
     *
     * @return the es pasantia
     */
    public Boolean getEsPasantia() {
        return esPasantia;
    }

    /**
     * Sets the es pasantia.
     *
     * @param esPasantia
     *            the new es pasantia
     */
    public void setEsPasantia(Boolean esPasantia) {
        this.esPasantia = esPasantia;
    }

}
