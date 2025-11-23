/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.CoinvestigadorAval
Objetivo 	: Clase POJO para la tabla HER_ARCHIVO_GRUPO en la cual se encuentran  
			  los archivos publicados como descargas por los grupos de investigación.
Creación	: Octubre 04 de 2007
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CoinvestigadorAval.
 */
public class CoinvestigadorAval implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3050312035921821131L;

    /** The id. */
    private Long id;

    /** The id persona. */
    private String idPersona;

    /** The id documento. */
    private String idDocumento;

    /** The facultad. */
    private String facultad;

    /** The departamento. */
    private String departamento;

    /** The horas. */
    private Long horas = 0L;

    /** The valor pagar. */
    private Long valorPagar = 0L;

    /** The aval. */
    private Aval aval;

    /** The nombre. */
    private String nombre;

    /** The tipo vinculacion. */
    private String tipoVinculacion;

    /** The tipo docente. */
    private String tipoDocente;

    /** The dependencia doc. */
    private String dependenciaDoc;

    /** The facultad doc. */
    private String facultadDoc;

    /** The nombre candidato. */
    private String nombreCandidato;

    /** The fecha nacimiento. */
    private Date fechaNacimiento;

    /** The lugar exp doc. */
    private String lugarExpDoc;

    /** The semanas formulacion. */
    private Long semanasFormulacion;

    /** The horas semana formulacion. */
    private Long horasSemanaFormulacion;

    /** The rol. */
    private String rol;

    /**
     * Instantiates a new coinvestigador aval.
     */
    public CoinvestigadorAval() {
        super();
        lugarExpDoc = "";
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
     * Gets the dependencia doc.
     *
     * @return the dependencia doc
     */
    public String getDependenciaDoc() {
        return dependenciaDoc;
    }

    /**
     * Sets the dependencia doc.
     *
     * @param dependenciaDoc
     *            the new dependencia doc
     */
    public void setDependenciaDoc(String dependenciaDoc) {
        this.dependenciaDoc = dependenciaDoc;
    }

    /**
     * Gets the facultad doc.
     *
     * @return the facultad doc
     */
    public String getFacultadDoc() {
        return facultadDoc;
    }

    /**
     * Sets the facultad doc.
     *
     * @param facultadDoc
     *            the new facultad doc
     */
    public void setFacultadDoc(String facultadDoc) {
        this.facultadDoc = facultadDoc;
    }

    /**
     * Gets the id persona.
     *
     * @return the id persona
     */
    public String getIdPersona() {
        return idPersona;
    }

    /**
     * Sets the id persona.
     *
     * @param idPersona
     *            the new id persona
     */
    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Gets the id documento.
     *
     * @return the id documento
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets the id documento.
     *
     * @param idDocumento
     *            the new id documento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * Gets the horas.
     *
     * @return the horas
     */
    public Long getHoras() {
        return horas;
    }

    /**
     * Sets the horas.
     *
     * @param horas
     *            the new horas
     */
    public void setHoras(Long horas) {
        this.horas = horas;
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
     * Gets the facultad.
     *
     * @return the facultad
     */
    public String getFacultad() {
        return facultad;
    }

    /**
     * Sets the facultad.
     *
     * @param facultad
     *            the new facultad
     */
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    /**
     * Gets the departamento.
     *
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Sets the departamento.
     *
     * @param departamento
     *            the new departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Gets the nombre candidato.
     *
     * @return the nombre candidato
     */
    public String getNombreCandidato() {
        return nombreCandidato;
    }

    /**
     * Sets the nombre candidato.
     *
     * @param nombreCandidato
     *            the new nombre candidato
     */
    public void setNombreCandidato(String nombreCandidato) {
        this.nombreCandidato = nombreCandidato;
    }

    /**
     * Gets the fecha nacimiento.
     *
     * @return the fecha nacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the fecha nacimiento.
     *
     * @param fechaNacimiento
     *            the new fecha nacimiento
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Gets the tipo vinculacion.
     *
     * @return the tipo vinculacion
     */
    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    /**
     * Sets the tipo vinculacion.
     *
     * @param tipoVinculacion
     *            the new tipo vinculacion
     */
    public void setTipoVinculacion(String tipoVinculacion) {
        this.tipoVinculacion = tipoVinculacion;
    }

    /**
     * Gets the lugar exp doc.
     *
     * @return the lugar exp doc
     */
    public String getLugarExpDoc() {
        return lugarExpDoc;
    }

    /**
     * Sets the lugar exp doc.
     *
     * @param lugarExpDoc
     *            the new lugar exp doc
     */
    public void setLugarExpDoc(String lugarExpDoc) {
        this.lugarExpDoc = lugarExpDoc;
    }

    /**
     * Gets the semanas formulacion.
     *
     * @return the semanas formulacion
     */
    public Long getSemanasFormulacion() {
        return semanasFormulacion;
    }

    /**
     * Sets the semanas formulacion.
     *
     * @param semanasFormulacion
     *            the new semanas formulacion
     */
    public void setSemanasFormulacion(Long semanasFormulacion) {
        this.semanasFormulacion = semanasFormulacion;
    }

    /**
     * Gets the horas semana formulacion.
     *
     * @return the horas semana formulacion
     */
    public Long getHorasSemanaFormulacion() {
        return horasSemanaFormulacion;
    }

    /**
     * Sets the horas semana formulacion.
     *
     * @param horasSemanaFormulacion
     *            the new horas semana formulacion
     */
    public void setHorasSemanaFormulacion(Long horasSemanaFormulacion) {
        this.horasSemanaFormulacion = horasSemanaFormulacion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        String uno = new String(this.idPersona);
        String dos = new String(((CoinvestigadorAval) obj).idPersona);
        if (uno.equals(dos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the tipo docente.
     *
     * @return the tipo docente
     */
    public String getTipoDocente() {
        return tipoDocente;
    }

    /**
     * Sets the tipo docente.
     *
     * @param tipoDocente
     *            the new tipo docente
     */
    public void setTipoDocente(String tipoDocente) {
        this.tipoDocente = tipoDocente;
    }

    /**
     * Gets the rol.
     *
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Sets the rol.
     *
     * @param rol
     *            the new rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Gets the valor pagar.
     *
     * @return the valor pagar
     */
    public Long getValorPagar() {
        return valorPagar;
    }

    /**
     * Sets the valor pagar.
     *
     * @param valorHora
     *            the new valor pagar
     */
    public void setValorPagar(Long valorHora) {
        this.valorPagar = valorHora;
    }

}
