package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class TipoDocumento.
 */
public class TipoDocumento implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 42784038045203098L;

    /** The Constant CEDULA. */
    public static final String CEDULA = "C";
    
    /** The Constant TARJETA_IDENTIDAD. */
    public static final String TARJETA_IDENTIDAD = "T";
    
    /** The Constant PASAPORTE. */
    public static final String PASAPORTE = "P";
    
    /** The Constant CEDULA_EXTRANJERIA. */
    public static final String CEDULA_EXTRANJERIA = "E";
    
    /** The Constant NIP. */
    public static final String NIP = "N";
    
    /** The Constant DESCONOCIDO. */
    public static final String DESCONOCIDO = "D";
    
    /** The Constant NIT_EXTRANJERIA. */
    public static final String NIT_EXTRANJERIA = "I";
    
    /** The Constant CODIGO. */
    public static final String CODIGO = "CD";

    /** The Constant NUMERO_IDENTIFICACION_TRIBUTARIA. */
    public static final String NUMERO_IDENTIFICACION_TRIBUTARIA = "NT";

    /** The Constant REGISTRO_CIVIL. */
    public static final String REGISTRO_CIVIL = "RC";

    /** The Constant CARNET_IDENTIDAD_POR_VISA. */
    public static final String CARNET_IDENTIDAD_POR_VISA = "IV";

    /** The Constant DOCUMENTO_IDENTIDAD_EXTRANJERA. */
    public static final String DOCUMENTO_IDENTIDAD_EXTRANJERA = "DE";

    /** The Constant CERTIFICADO_CABILDO. */
    public static final String CERTIFICADO_CABILDO = "CA";

    /** The id. */
    private String id;
    
    /** The nombre. */
    private String nombre;

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the new nombre
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
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }
}
