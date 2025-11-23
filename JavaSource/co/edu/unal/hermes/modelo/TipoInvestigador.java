package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class TipoInvestigador.
 */
public class TipoInvestigador implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1064001108053881924L;

    /** The Constant JOVEN_INVESTIGADOR_POSGRADO. */
    public static final String JOVEN_INVESTIGADOR_POSGRADO = "JIPO";

    /** The Constant JOVEN_INVESTIGADOR_EGRESADO. */
    public static final String JOVEN_INVESTIGADOR_EGRESADO = "JIEU";

    /** The Constant JOVEN_INVESTIGADOR_EGRESADO. */
    public static final String PROFESOR_CARRERA_DOCENTE = "PCD";

    /** The Constant JOVEN_INVESTIGADOR_EGRESADO. */
    public static final String ADMINISTRATIVO = "AD";

    /** The Constant JOVEN_INVESTIGADOR_EGRESADO. */
    public static final String PROFESOR_NO_CARRERA_DOCENTE = "PSCD";
    
    /** The estudiantePregrado. */
    public static final String ESTUDIANTE_PREGRADO = "ESPR";
    
    /** The estudiantePosgrado. */
    public static final String ESTUDIANTE_POSGRADO = "ESPO";

    /** The Principal. */
    public static final String Principal = "P";

    /** The coinvestigador. */
    public static final String coinvestigador = "C";

    /** The director. */
    public static final String DIRECTOR = "P";

    /** The codirector. */
    public static final String CODIRECTOR = "CES";

    /** The director externo. */
    public static final String DIRECTOR_EXTERNO = "DE";

    /** Id de autor y coautor en solicitud de isbn. */
    public static final String AUTOR_ISBN = "ISAU";

    /** The Constant COAUTOR_ISBN. */
    public static final String COAUTOR_ISBN = "ISCA";

    /** The id. */
    private String id;

    /** The nombre. */
    private String nombre;

    /** The tipo modalidad. */
    private String tipoModalidad;

    /** The documento. */
    private String documento;

    /** The tipo documento. */
    private String tipoDocumento;

    /** The es externo. */
    private String tipo;

    /** The tipo vinculacion. */
    private String tipoVinculacion;

    /** The crear investigador. */
    private boolean crearInvestigador;

    /** The crear investigador. */
    private boolean mostrarProgramaAcademico;

    /** The mostrar dependencia. */
    private boolean mostrarDependencia;

    /** The enviar correo contrasenia. */
    private boolean enviarCorreoContrasenia;
    
    /** Visible. */
    private boolean esVisible;

    /**
     * Gets the tipo modalidad.
     *
     * @return the tipo modalidad
     */
    public String getTipoModalidad() {
        return tipoModalidad;
    }

    /**
     * Sets the tipo modalidad.
     *
     * @param tipoModalidad
     *            the new tipo modalidad
     */
    public void setTipoModalidad(String tipoModalidad) {
        this.tipoModalidad = tipoModalidad;
    }

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
     * @param nombre
     *            the new nombre
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object tipoInv) {

        TipoInvestigador tipoInvestig = (TipoInvestigador) tipoInv;
        return this.id.equals(tipoInvestig.getId());
    }

    /**
     * Gets the documento.
     *
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Sets the documento.
     *
     * @param documento
     *            the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Gets the tipo documento.
     *
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the tipo documento.
     *
     * @param tipoDocumento
     *            the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the tipo vinculacion.
     *
     * @return the tipoVinculacion
     */
    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    /**
     * Sets the tipo vinculacion.
     *
     * @param tipoVinculacion
     *            the tipoVinculacion to set
     */
    public void setTipoVinculacion(String tipoVinculacion) {
        this.tipoVinculacion = tipoVinculacion;
    }

    /**
     * Determina si es autor de obra en solicitud de isbn.
     *
     * @return true, if is es autor isbn
     */
    public boolean isEsAutorIsbn() {
        if (this.id != null && this.id.equals(AUTOR_ISBN)) {
            return true;
        }
        return false;
    }

    /**
     * Determina si es coautor de obra en solicitud de isbn.
     *
     * @return true, if is es coautor isbn
     */
    public boolean isEsCoautorIsbn() {
        if (this.id != null && this.id.equals(COAUTOR_ISBN)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is crear investigador.
     *
     * @return the crearInvestigador
     */
    public boolean isCrearInvestigador() {
        return crearInvestigador;
    }

    /**
     * Sets the crear investigador.
     *
     * @param crearInvestigador
     *            the crearInvestigador to set
     */
    public void setCrearInvestigador(boolean crearInvestigador) {
        this.crearInvestigador = crearInvestigador;
    }

    /**
     * Checks if is mostrar programa academico.
     *
     * @return true, if is mostrar programa academico
     */
    public boolean isMostrarProgramaAcademico() {
        return mostrarProgramaAcademico;
    }

    /**
     * Sets the mostrar programa academico.
     *
     * @param mostrarProgramaAcademico
     *            the new mostrar programa academico
     */
    public void setMostrarProgramaAcademico(boolean mostrarProgramaAcademico) {
        this.mostrarProgramaAcademico = mostrarProgramaAcademico;
    }

    /**
     * Checks if is mostrar dependencia.
     *
     * @return true, if is mostrar dependencia
     */
    public boolean isMostrarDependencia() {
        return mostrarDependencia;
    }

    /**
     * Sets the mostrar dependencia.
     *
     * @param mostrarDependencia
     *            the new mostrar dependencia
     */
    public void setMostrarDependencia(boolean mostrarDependencia) {
        this.mostrarDependencia = mostrarDependencia;
    }

    /**
     * Checks if is enviar correo contrasenia.
     *
     * @return the enviarCorreoContrasenia
     */
    public boolean isEnviarCorreoContrasenia() {
        return enviarCorreoContrasenia;
    }

    /**
     * Sets the enviar correo contrasenia.
     *
     * @param enviarCorreoContrasenia            the enviarCorreoContrasenia to set
     */
    public void setEnviarCorreoContrasenia(boolean enviarCorreoContrasenia) {
        this.enviarCorreoContrasenia = enviarCorreoContrasenia;
    }

	public boolean isEsVisible() {
		return esVisible;
	}

	public void setEsVisible(boolean esVisible) {
		this.esVisible = esVisible;
	}

}
