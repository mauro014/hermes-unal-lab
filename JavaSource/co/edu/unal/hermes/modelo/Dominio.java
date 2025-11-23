package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class Dominio.
 */
public class Dominio implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7491614527435246057L;

    /** The Constant TIPOS_ACTO_ADMINISTRATIVO. */
    public static final String TIPOS_ACTO_ADMINISTRATIVO = "TIPO_ACTO_ADMINISTRATIVOS";
    
    /** The Constant CARACTER_OBRA_PROP_INT. */
    public static final String CARACTER_OBRA_PROP_INT = "149";
    
    /** The Constant AMBITO_OBRA_LITERARIA_PROP_INT. */
    public static final String AMBITO_OBRA_LITERARIA_PROP_INT = "150";
    
    /** The Constant TIPO_EDICION_OBRA_PROP_INT. */
    public static final String TIPO_EDICION_OBRA_PROP_INT = "151";
    
    /** The Constant CLASE_OBRA_PROP_INT. */
    public static final String CLASE_OBRA_PROP_INT = "157";
    
    /** The Constant TIPO_FORMATO_PROP_INT. */
    public static final String TIPO_FORMATO_PROP_INT = "158";
    
    /** The Constant TIPO_RESULTADO_PROP_INT. */
    public static final String TIPO_RESULTADO_PROP_INT = "159";

    /** The Constant TIPO_ORDENADOR_GASTO. */
    public static final String TIPO_ORDENADOR_GASTO = "TIPO_ORDENADOR_GASTO";

    /** The Constant NAT_ENTIDAD. */
    public static final String NAT_ENTIDAD = "EXT_NATURALEZA_ENTIDAD";
    
    /** The Constant CARACTER_FUENTE_FINANCIACION. */
    public static final String CARACTER_FUENTE_FINANCIACION = "CARACTER_FUENTE_FINANCIACION";
    
    /** The Constant TIPO_FUENTE_FINANCIACION. */
    public static final String TIPO_FUENTE_FINANCIACION = "TIPO_FUENTE_FINANCIACION";
    
    public static final String DOMINIO_GRUPO_DIRIGE_EVENTOS = "DOMINIO_GRUPO_DIRIGE_EVENTOS";
    
    public static final String DOMINIO_POBLACION_DIRIGE_EVENTOS = "DOMINIO_POBLACION_DIRIGE_EVENTOS";
    
    public static final String DOMINIO_POBLACION_OBJETIVO_EVENTO = "226";
    
    public static final String DOMINIO_AREA_GESTION_CONOCIMIENTO = "287";
    
    public static final String DOMINIO_ODS= "212";
    
    public static final String DOMINIO_AREA_CIENCIA = "68";
    
    public static final String DOMINIO_ESTADO_COL_AUTORIDAD = "ESTADO_COL_AUTORIDAD";
    public static final String DOMINIO_CLASIF_COL = "CLASIFICACION_COLECCION";
    public static final String DOMINIO_TIPO_OBJETOS = "TIPOS_OBJETOS_COLECCION";
    public static final String ID_DOMINIO_TIPO_OBJETOS = "314";
    public static final String DOMINIO_SUBTIPO_OBJETOS = "SUBTIPO_OBJ_COLECCION";
    public static final String ID_DOMINIO_SUBTIPO_OBJETOS = "315";
    public static final String DOMINIO_AVAL_SUBTIPO = "SUBTIPO_AVAL_ESPECIFICACION";
    public static final String ID_DOMINIO_AVAL_SUBTIPO = "318";
    public static final String ID_DOMINIO_TIPO_GESTION_COL = "322";
    
    /** The id. */
    Long id;
    
    /** The Tipo. */
    String Tipo;

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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param Tipo
     *            the new tipo
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

}
