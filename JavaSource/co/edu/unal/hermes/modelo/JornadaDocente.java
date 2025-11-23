package co.edu.unal.hermes.modelo;

/**
 * Especifíca la información de la jornada docente como tipo de modalidad para la inscripcion de proyectos 
 */
public class JornadaDocente extends Modalidad{
    
    
    private int semestre;
    private int año;
    private String descripcion;
    private EstadoConvocatoria estadoJornadaDocente;
    
    public int getAño() {
        return año;
    }
    public void setAño(int año) {
        this.año = año;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getSemestre() {
        return semestre;
    }
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    public EstadoConvocatoria getEstadoJornadaDocente() {
        return estadoJornadaDocente;
    }
    public void setEstadoJornadaDocente(EstadoConvocatoria estadoJornadaDocente) {
        this.estadoJornadaDocente = estadoJornadaDocente;
    }
}