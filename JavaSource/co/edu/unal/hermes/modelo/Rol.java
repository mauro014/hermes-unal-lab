package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Juan Pablo
 */

public class Rol implements Serializable {

    private static final long serialVersionUID = 7426292140416715360L;

    public static final String ADMINISTRAR_DOCUMENTOS = "DV";
    public static final String ASESOR = "A";
    public static final String DIRECCION_INVESTIGACION = "AD";
    public static final String VICEDECANATURA_INVESTIGACION = "AF";
    public static final String VICERRECTORIA_INVESTIGACION = "AV";
    public static final String AVALAR_GRUPOS = "AG";
    public static final String COMUNICACIONES = "PG";
    public static final String EGRESADO = "E2";
    public static final String ADMINISTRADOR_CONVOCATORIAS = "M";
    public static final String ADMINISTRADOR_HERMES = "AH";
    public static final String INVESTIGADOR = "I";
    public static final String EVALUADOR = "E";
    public static final String COORDINADOR = "C";
    public static final String BECARIO = "B";
    public static final String EDITOR_REVISTA = "ER";
    public static final String JEFEUNIDAD = "JU";
    public static final String CONSULTA_LABORATORIOS = "CL";
    public static final String NOMBRE_CONSULTA_LABORATORIOS = "Consulta laboratorios";
    public static final String DIRECCION_NACIONAL_LABORATORIOS = "DL";
    public static final String DIRECCION_LABORATORIOS_SEDE = "LS";
    public static final String DIAGNOSTICO_SOFWARE = "DS";
    public static final String NOMBRE_DIAGNOSTICO_SOFWARE = "Diagnóstico software";
    public static final String ED_CONT_FACULTAD = "EF";
    public static final String EDUCACION_CONTINUA_FACULTAD = "Educación continua facultad";
    public static final String EDITORIAL = "ED";
    public static final String COORDINADOR_LABORATORIO = "CO";
    public static final String NOMBRE_COORDINADOR_LABORATORIO = "Coordinador laboratorio";
    public static final String LABORATORIOS_FACULTAD = "LF";
    public static final String NOMBRE_ROL_LABORATORIOS_FACULTAD = "Laboratorios facultad";
    public static final String LABORATORIOS_DEPARTAMENTO = "LD";
    public static final String NOMBRE_ROL_LABORATORIOS_DEPTO = "Laboratorios departamento";
    public static final String REQUERIMIENTOS = "Requerimientos";
    public static final String INVENTARIOS_LABORATORIOS = "IL";
    public static final String NOMBRE_ROL_INVENTARIOS_LABORATORIOS = "Inventarios laboratorios";
    public static final String INNOVACION = "IN";
    public static final String COORDINADOR_TECNICO_LABORATORIO = "CT";
    public static final String NOMBRE_ROL_COORDINADOR_TECNICO_LABORATORIO = "Coordinador/ jefe técnico laboratorio";
    public static final String TECNICO_LABORATORISTA = "TL";
    public static final String NOMBRE_ROL_TECNICO_LABORATORISTA = "Laboratorista";
    public static final String DIRECTOR_CALIDAD_LABORATORIO = "DC";
    public static final String NOMBRE_ROL_DIRECTOR_CALIDAD_LABORATORIO = "Coordinador/líder de calidad laboratorio";
    public static final String PROPIEDAD_INTELECTUAL = "PI";
    public static final String ADMINISTRAR_SOLICITUDES_USUARIO = "Administrar Solicitudes Usuario";
    public static final String CREAR_SOLICITUDES_USUARIO = "Crear Solicitudes Usuario";
    public static final String EVALUACION_PROVEEDORES = "EV";
    public static final String NOMBRE_ROL_EVALUACION_PROVEEDORES = "Evaluación proveedores";
    public static final String UNIDAD_ADMINISTRATIVA_INVESTIGACION = "UA";
    public static final String UNIDAD_ADMINISTRATIVA_EXTENSION = "UE";
    public static final String MOVILIDADES_FACULTAD = "MF";
    public static final String MOVILIDADES_SEDE = "MD";
    public static final String DIR_NAL_EXTENSION = "DE";
    public static final String CURSOS_FORMACION = "FO";
    public static final String OF_EXTENSION_FACULTAD = "OE";
    public static final String DIRECTOR_UAB = "DD";
    public static final String AVAL_EXTENSION = "AE";
    public static final String CURADOR = "CU";
    public static final String CENTRO_EXTENSION = "CX";
    public static final String REQUERIMIENTO = "RQ";
    public static final String ADM_REQUERIMIENTO = "AR";
    public static final String DECANO = "D";
    public static final String INDICADORES = "ID";
    public static final String VICERRECTOR = "V";
    public static final String BECA_DOCTORADO = "BD";
    public static final String REVISION_RENOVACION = "RD";
    public static final String DIR_NAL_INNOVA_PI = "PN";
    public static final String CONSULTA = "CN";
    public static final String JOVEN_INVESTIGADOR = "JI";
    public static final String SOLICITUD_USUARIO = "RU";
    public static final String ADM_SOLICITUD_USUARIO = "SU";
    public static final String CENTRO_EDITORIAL_FAC = "FA";
    public static final String CORREDOR_TECNOLOGICO_AGRO = "TA";
    public static final String PROPIEDAD_INTELECTUAL_SEDE = "PS";
    public static final String CONSULTA_REQUERIMIENTOS = "CR";
    public static final String ESTUDIANTE_LIDER = "AL";
    public static final String ASISTENTE_LIDER = "ADL";
    public static final String SUPER_USUARIO = "SP";
    
    public static final String AVAL_DRE = "AI";
    public static final String COORDINADOR_EDITORIAL = "EC";
    public static final String AVAL_CEPI = "CEPI";
    public static final String AVAL_CESI = "CESI";
	public static final String RECTORIA = "RE";
	public static final String ASESOR_EDITORIAL = "AED";
	public static final String NOMBRE_ASESOR_EDITORIAL = "Asesor editorial";
	public static final String EDITORIAL_UN = "EUN";
	
	public static final String LAB_INVESTIGADOR_DOCENTE = "LID";
	public static final String LAB_ESTUDIANTE_INVESTIGADOR = "LEI";
	public static final String LAB_ESTUDIANTE_AUXILIAR = "LEA";
	public static final String LAB_PERSONAL_APOYO = "LPA";

    private String id;
    private String nombre;
    private String comentario;
    private String visible;
    
    private Date fechaVencimientoPersonaRol; // Variable no mapeada, se utiliza para mostrar la fecha de vencimiento del rol para la persona específica

    private Set servicios = new HashSet();

    public Rol() {

    }

    public Rol(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreMinusculas() {
        String nombreRol = "";
        if (this.nombre != null) {
            nombreRol = nombre.substring(0, 1).toUpperCase() + nombre.toLowerCase().substring(1, nombre.length());
        }
        return nombreRol;
    }

    public Set getServicios() {
        return servicios;
    }

    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }

    public void adicionarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Rol) {
            Rol rol = (Rol) obj;

            return rol.getId().equals(this.id);
        }
        return false;
    }

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public Date getFechaVencimientoPersonaRol() {
		return fechaVencimientoPersonaRol;
	}

	public void setFechaVencimientoPersonaRol(Date fechaVencimientoPersonaRol) {
		this.fechaVencimientoPersonaRol = fechaVencimientoPersonaRol;
	}
}
