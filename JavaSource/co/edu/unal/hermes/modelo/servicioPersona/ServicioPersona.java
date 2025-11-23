/*
 * Created on 09-ago-20
 */
package co.edu.unal.hermes.modelo.servicioPersona;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.bd.IDependenciaDAO;
import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.bd.IPersonaDAO;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Evaluador;
import co.edu.unal.hermes.modelo.EvaluadorCorreo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;

public class ServicioPersona implements IServicioPersona {

    private IPersonaDAO personaDAO;
    private IGeneralDAO generalDAO;
    private IDependenciaDAO dependenciaDAO;
    private String mensajeError;
    private String mensajeRol;
    private String mensajeEvaluador;
    private String mensajeInvestigador;

    public Persona obtenerCoordinadorNoAsignadoAsesor(IdPersona idAsesor, String idCoordinador) {
        return personaDAO.obtenerCoordinadorNoAsignadoAsesor(idAsesor, idCoordinador);
    }
    
    public Persona obtenerCoordinadorNoAsignadoAsesorEditorial(IdPersona idAsesor, String idCoordinador) {
        return personaDAO.obtenerCoordinadorNoAsignadoAsesorEditorial(idAsesor, idCoordinador);
    }

    public List obtenerGruposInvestigadorLiderIntersede(IdPersona id) throws DataAccessException {
        return personaDAO.obtenerGruposInvestigadorLiderIntersede(id);
    }

    public List obtenerPersona(String where, boolean isParametros, Object[] parametros) throws DataAccessException {
        return personaDAO.obtenerPersona(where, isParametros, parametros);
    }

    public List obtenerPersonasxRolId(Rol r, String id, String tipodoc) throws DataAccessException {

        return personaDAO.obtenerPersonasxRolId(r, id, tipodoc);
    }
    
    public List obtenerPersonasxRolIdxDpnId(String rol, String dpn) throws DataAccessException {
        return personaDAO.obtenerPersonasxRolIdxDpnId(rol, dpn);
    }
    
    public List<Persona> obtenerPersonasxRolIdxSedeId(String rol, Long sedeId) throws DataAccessException {
        return personaDAO.obtenerPersonasxRolIdxSedeId(rol, sedeId);
    }
    
    public List obtenerPersonasUnidadAdministrativa(String dpn, String sede) throws DataAccessException {
        return personaDAO.obtenerPersonasUnidadAdministrativa(dpn, sede);
    }

    public List obtenerInvestigadores(String where, boolean isParametros, Object[] parametros) {
        return personaDAO.obtenerInvestigadores(where, isParametros, parametros);
    }

    public String getMensajeInvestigador() {
        return mensajeInvestigador;
    }

    public void setMensajeInvestigador(String mensajeInvestigador) {
        this.mensajeInvestigador = mensajeInvestigador;
    }

    public String getMensajeEvaluador() {
        return mensajeEvaluador;
    }

    public void setMensajeEvaluador(String mensajeEvaluador) {
        this.mensajeEvaluador = mensajeEvaluador;
    }

    public String getMensajeRol() {
        return mensajeRol;
    }

    public void setMensajeRol(String mensajeRol) {
        this.mensajeRol = mensajeRol;
    }

    public void setPersonaDAO(IPersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    public void setGeneralDAO(IGeneralDAO generalDAO) {
        this.generalDAO = generalDAO;
    }

    public List obtenerInvestigadoresPorNombre(String name) {
        List a = null;
        try {
            a = personaDAO.obtenerInvestigadores(name);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * Se obtienen los investigadores que tienen los nombres y apellidos pasados
     * como parametros en la función
     */
	public List obtenerInvestigadoresPorNombresYApellidos(String nombres, String apellidos, Boolean busqExacta) {
		List resultado = new ArrayList();
		List opciones = opcionesDeBusquedaInvestigador(nombres, apellidos);
		if (busqExacta) {
			opciones = new ArrayList();
			String[] valor1 = new String[4];
			String[] cadenas = nombres.split(" ");
			valor1[0] = cadenas[0];
			if (cadenas.length > 1) {
				valor1[1] = cadenas[1];
			} else {
				valor1[1] = "";
			}
			cadenas = apellidos.split(" ");
			if (cadenas != null) {
				valor1[2] = cadenas[0];
			} else {
				valor1[2] = "";
			}
			if (cadenas.length > 1) {
				valor1[3] = cadenas[1];
			} else {
				valor1[3] = "";
			}
			opciones.add(valor1);
		}
		Iterator it = opciones.iterator();
		while (it.hasNext()) {
			String[] valores = (String[]) it.next();
			List investigadores = personaDAO.obtenerInvestigadores(valores[0], valores[1], valores[2], valores[3],
					busqExacta);
			resultado.addAll(investigadores);
		}
		return resultado;
	}

    /**
     * Se obtienen los investigadores que tienen los nombres y apellidos pasados
     * como parametros en la función sin importar las tildes y mayusculas
     */
    public List obtenerInvestigadoresPorNombresYApellidosIndiferenteTildesYMayusculas(String nombres,
            String apellidos) {
        List resultado = new ArrayList();
        List opciones = opcionesDeBusquedaInvestigador(nombres, apellidos);
        Iterator it = opciones.iterator();
        while (it.hasNext()) {
            String[] valores = (String[]) it.next();
            List investigadores = personaDAO.obtenerInvestigadoresIndeferenteTildesYMayusculas(valores[0], valores[1],
                    valores[2], valores[3]);
            resultado.addAll(investigadores);
        }
        return resultado;
    }

    public Investigador obtenerDocente(IdPersona id) { // Jassar 26 de mayo 2005
        return personaDAO.obtenerDocente(id);
    }

    public Investigador obtenerInvestigador(IdPersona id) {
        return personaDAO.obtenerInvestigador(id);
    }

    public Investigador obtenerInvestigadorProyectos(IdPersona id) {
        return personaDAO.obtenerInvestigadorProyectos(id);
    }

    public Investigador obtenerInvestigadorSinProyectos(IdPersona id) {
        return personaDAO.obtenerInvestigadorSinProyectos(id);
    }

    public Investigador obtenerProyectosInvestigador(IdPersona id) {
        return personaDAO.obtenerProyectosInvestigador(id);
    }
	
	public Investigador obtenerProyectosInvestigador(IdPersona id, boolean incluirCreadosPor, int conv_padre_id) {
		return personaDAO.obtenerProyectosInvestigador(id,incluirCreadosPor,conv_padre_id);
	}

    public Investigador obtenerProyectosGruposInvestigador(IdPersona id) {
        return personaDAO.obtenerProyectosGruposInvestigador(id);
    }

    public List obtenerInvestigadorPrincipalProyectos(IdPersona id) {
        return personaDAO.obtenerInvestigadorPrincipalProyectos(id);
    }

    public Investigador obtenerResumenInvestigador(IdPersona id) {
        return personaDAO.obtenerResumenInvestigador(id);
    }

    /**
     * Guarda un investigador asignandole el rol investigador
     */
    public void guardarInvestigador(Investigador inv) {
        // Se crea el rol investigador para asignarlo a la persona
        Rol rol = new Rol();
        rol.setId(Rol.INVESTIGADOR);
        inv.adicionarRol(rol);
        personaDAO.guardarInvestigador(inv);
    }
    
    /**
     * Guarda un investigador asignandole con opción o no de asignarle rol de investigador.
     */
    public void guardarInvestigador(Investigador inv, boolean rolInvestigador) {
        // Se crea el rol investigador para asignarlo a la persona
    	if(rolInvestigador) {
	        Rol rol = new Rol();
	        rol.setId(Rol.INVESTIGADOR);
	        inv.adicionarRol(rol);
    	}
        personaDAO.guardarInvestigador(inv);
    }

    public Persona obtenerPersona(IdPersona id) {
        return personaDAO.obtenerPersona(id);
    }

    public InvestigadorExterno obtenerInvestigadorExterno(IdPersona id) {
        return personaDAO.obtenerInvestigadorExterno(id);
    }

    public Persona obtenerPersonaRoles(IdPersona id) {
        return personaDAO.obtenerPersonaRoles(id);
    }

    public Rol obtenerRol(String id) {
        return personaDAO.obtenerRol(id);
    }

    public List<InvestigadorGrupo> obtenerGruposInvestigador(Investigador investigador) {
        return personaDAO.obtenerGruposInvestigador(investigador);
    }

    public List obtenerGruposInvestigadorMovilidades(Investigador investigador) throws DataAccessException {
        return personaDAO.obtenerGruposInvestigadorMovilidades(investigador);
    }

    public Investigador buscarInvestigadorParaAsociarAProyecto(IdPersona id) {
        Investigador investigador = personaDAO.obtenerInvestigadorCategoria(id);
        if (investigador == null) {
            // Si no existe el investigador se busca como estudiante
            Estudiante estudiante = obtenerEstudiante(id);
            if (estudiante != null) {
                // Si es un estudiante se convierte en investigador pero no se
                // guarda hasta que el investigador principal lo acepte como
                // investigador
                investigador = estudiante.convertirAInvestigador();
                // guardarInvestigador(investigador);
            }
        }
        return investigador;
    }

    public Estudiante obtenerEstudiante(IdPersona id) {
        return personaDAO.obtenerEstudiante(id);
    }

    /**
     * Asigna a una persona el rol Asesor y lo guarda
     */
    public void guardarAsesor(Persona persona) {
        // Se crea el rol asesor para asignarlo a la persona
        Rol rol = new Rol();
        rol.setId(Rol.ASESOR);
        persona.adicionarRol(rol);
        // guarda en la base de datos la persona
        personaDAO.guardarPersona(persona);
    }

    public List obtenerGruposInvestigadorPrincipal(IdPersona id) {
        return personaDAO.obtenerGruposInvestigadorPrincipal(id);
    }

    public List<Grupo> obtenerGruposInvestigadorLider(IdPersona id) {
        return personaDAO.obtenerGruposInvestigadorLider(id);
    }

    public InvestigadorInterno obtenerInvestigadorInterno(IdPersona id) {
        return personaDAO.obtenerInvestigadorInterno(id);
    }

    public Date obtenerFechaFinRol(IdPersona id, String idRol) {
        return personaDAO.obtenerFechaFinRol(id, idRol);
    }

    public Evaluador obtenerEvaluadorProyectos(IdPersona id) {
        return personaDAO.obtenerEvaluadorProyectos(id);
    }

    public Evaluador obtenerEvaluador(IdPersona id) {
        return personaDAO.obtenerEvaluador(id);
    }

    /**
     * Funcion que recive como parametros los nombres y apellidos del
     * investigador y encuentra todas las posibles opciones de nombres que se
     * obienten con los datos dados El valor de retorno es una lista de objetos
     * de tipo "String [4]", en la cual se encuentran en las posiciones de 0 a 3
     * el nombre1, nombre2, apellido1, apellido2 reespectivamente
     * 
     * @param nombre
     * @param apellido
     * @return List
     */
    private static List opcionesDeBusquedaInvestigador(String nombre, String apellido) {

        String[] valor1 = new String[4];
        String[] valor2 = new String[4];
        String[] valor3 = new String[4];
        String[] valor4 = new String[4];

        List resultado = new ArrayList();

        StringTokenizer stn = new StringTokenizer(nombre != null ? nombre : "", " ");
        StringTokenizer sta = new StringTokenizer(apellido != null ? apellido : "", " ");

        int tokensNombre = stn.countTokens();
        int tokensApellido = sta.countTokens();

        String nombre1 = "";
        String nombre2 = "";
        String apellido1 = "";
        String apellido2 = "";

        if (stn.countTokens() == 1) {
            nombre1 = stn.nextToken();
        } else if (stn.countTokens() >= 2) {
            nombre1 = stn.nextToken();
            nombre2 = stn.nextToken();
        }

        if (sta.countTokens() == 1) {
            apellido1 = sta.nextToken();
        } else if (sta.countTokens() >= 2) {
            apellido1 = sta.nextToken();
            apellido2 = sta.nextToken();
        }

        if (tokensNombre >= 1) {
            if (tokensApellido >= 1) {
                valor1[0] = nombre1;
                valor1[1] = nombre2;
                valor1[2] = apellido1;
                valor1[3] = apellido2;
                valor2[0] = nombre1;
                valor2[1] = nombre2;
                valor2[2] = apellido2;
                valor2[3] = apellido1;
                valor3[0] = nombre2;
                valor3[1] = nombre1;
                valor3[2] = apellido1;
                valor3[3] = apellido2;
                valor4[0] = nombre2;
                valor4[1] = nombre1;
                valor4[2] = apellido2;
                valor4[3] = apellido1;
                resultado.add(valor1);
                resultado.add(valor2);
                resultado.add(valor3);
                resultado.add(valor4);
            } else if (tokensApellido == 0) {
                valor1[0] = nombre1;
                valor1[1] = nombre2;
                valor1[2] = apellido1;
                valor1[3] = apellido2;
                valor2[0] = nombre2;
                valor2[1] = nombre1;
                valor2[2] = apellido1;
                valor2[3] = apellido2;
                resultado.add(valor1);
                resultado.add(valor2);
            }
        } else if (tokensNombre == 0) {
            if (tokensApellido >= 1) {
                valor1[0] = nombre1;
                valor1[1] = nombre2;
                valor1[2] = apellido1;
                valor1[3] = apellido2;
                valor2[0] = nombre1;
                valor2[1] = nombre2;
                valor2[2] = apellido2;
                valor2[3] = apellido1;
                resultado.add(valor1);
                resultado.add(valor2);
            } else if (tokensApellido == 0) {
                valor1[0] = nombre1;
                valor1[1] = nombre2;
                valor1[2] = apellido1;
                valor1[3] = apellido2;
                resultado.add(valor1);
            }
        }
        return resultado;
    }

    public List obtenerInvestigadoresInternos(String name) {
        return personaDAO.obtenerInvestigadoresInternos(name);
    }

    public int obtenerNumeroDeInvestigadoresCategoriaDeProyecto(Proyecto proyecto,
            CategoriaInvestigador categoriaInvestigador) {
        return personaDAO.obtenerNumeroDeInvestigadoresCategoriaDeProyecto(proyecto, categoriaInvestigador);
    }

    public InvestigadorInterno obtenerInvestigadorClasificacionConocimiento(IdPersona id) {
        return personaDAO.obtenerInvestigadorClasificacionConocimiento(id);
    }

    public List obtenerInvestigadoresPorNombresApellidosDependencia(String nombreInvestigador,
            String apellidoInvestigador, Dependencia dependencia, String tipoDependencia) {

        List resultado = new ArrayList();
        List opciones = opcionesDeBusquedaInvestigador(nombreInvestigador, apellidoInvestigador);
        Iterator it = opciones.iterator();
        while (it.hasNext()) {
            String[] valores = (String[]) it.next();
            List investigadores = personaDAO.obtenerInvestigadoresPorNombresApellidosDependencia(valores[0], valores[1],
                    valores[2], valores[3], dependencia, tipoDependencia);
            resultado.addAll(investigadores);
        }
        return resultado;
    }

    public List obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(
            String nombreInvestigador, String apellidoInvestigador, Dependencia dependencia, String tipoDependencia) {

        List resultado = new ArrayList();
        List opciones = opcionesDeBusquedaInvestigador(nombreInvestigador, apellidoInvestigador);
        Iterator it = opciones.iterator();
        while (it.hasNext()) {
            String[] valores = (String[]) it.next();
            List investigadores = personaDAO
                    .obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(valores[0],
                            valores[1], valores[2], valores[3], dependencia, tipoDependencia);
            resultado.addAll(investigadores);
        }
        return resultado;
    }

    public List obtenerPersonasxRol(Rol r) {
        return personaDAO.obtenerPersonasxRol(r);
    }

    /**
     * Obtiene los proyectos a los cuales les esta haciendo seguimiento
     */
    public List obtenerProyectosAsesor(Persona persona) {
        return personaDAO.obtenerProyectosAsesor(persona.getId());
    }

    public List obtenerProyectosAsesor(Persona persona, Modalidad m) {
        return personaDAO.obtenerProyectosAsesor(persona.getId(), m);
    }

    public List obtenerPosiblesEvaluadoresInternos(ClasificacionConocimiento cla, Modalidad m) {
        return personaDAO.obtenerPosiblesEvaluadoresInternos(cla, m);
    }

    public List obtenerPosiblesEvaluadoresExternos(ClasificacionConocimiento cla) {
        return personaDAO.obtenerPosiblesEvaluadoresExternos(cla);
    }

    public InvestigadorExterno obtenerInvestigadorExternoCompleto(IdPersona id) {
        return personaDAO.obtenerInvestigadorExternoCompleto(id);
    }

    public InvestigadorInterno obtenerInvestigadorInternoCompleto(IdPersona id) {
        return personaDAO.obtenerInvestigadorInternoCompleto(id);
    }

    public List obtenerInvestigadoresPertenecenDependencia(Dependencia dependencia) {
        return recorrerArbol(dependencia);
    }

    /**
     * @param dependencia
     */
    private List recorrerArbol(Dependencia dependencia) {
        List l = new ArrayList();
        l.addAll(personaDAO.obtenerInvestigadoresXDependencia(dependencia));
        List hijos = generalDAO.obtenerHijos(dependencia);
        if (hijos != null && hijos.size() != 0) {
            Iterator it = hijos.iterator();
            while (it.hasNext()) {
                // l.addAll(recorrerArbol((Dependencia) it.next()));
                l.addAll(personaDAO.obtenerInvestigadoresXDependencia((Dependencia) it.next()));
            }
        }
        return l;
    }

    public Investigador obtenerInvestigadorProyectosAEvaluar(IdPersona id) {
        return personaDAO.obtenerInvestigadorProyectosAEvaluar(id);
    }

    public List obtenerParticipantesConvocatoria(Modalidad modalidad) {
        return personaDAO.obtenerParticipantesConvocatoria(modalidad);
    }

    public Long obtenerNumeroProyectosEvaluador(Persona p, Modalidad m) {
        return personaDAO.obtenerNumeroProyectosEvaluador(p, m);
    }

    public List obtenerPosiblesEvaluadoresxPalabraClave(Proyecto p, Modalidad m) {
        return personaDAO.obtenerPosiblesEvaluadoresxPalabraClave(p, m);
    }

    public long generarClaveExterno() {
        return personaDAO.generarClaveExterno();
    }

    public String login(Persona ie) {
        if (ie == null) {
            return "<<login>>";
        }
        String l = (ie.getNombre1() == null || ie.getNombre1().length() < 1 ? "" : ie.getNombre1().substring(0, 1))
                + (ie.getNombre2() == null || ie.getNombre2().length() < 1 ? "" : ie.getNombre2().substring(0, 1))
                + (ie.getApellido1() == null ? "" : ie.getApellido1())
                + (ie.getApellido2() == null || ie.getApellido2().length() < 1 ? ""
                        : ie.getApellido2().substring(0, 1));
        return l.toUpperCase();

    }

    public InvestigadorExterno buscarInvestigadorExternoId(String documento, String tipo) {
        return personaDAO.buscarInvestigadorExternoId(documento, tipo);
    }

    public List obtenerProyectosAsesorConListaEvaluadoresProyecto(IdPersona id, Modalidad m) {
        return personaDAO.obtenerProyectosAsesorConListaEvaluadoresProyecto(id, m);
    }

    public InvestigadorInterno obtenerInvestigadorInternoDependenciaYFacultad(IdPersona id) {
        return personaDAO.obtenerInvestigadorInternoDependenciaYFacultad(id);
    }

    public List<Persona> buscarPersonaInvestigadoresExternos(long clave) {
        return personaDAO.buscarPersonaInvestigadoresExternos(clave);
    }

    public Investigador obtenerInvestigadorProyectosPropuestosAEvaluar(IdPersona id) {
        return personaDAO.obtenerInvestigadorProyectosPropuestosAEvaluar(id);
    }

    public List obtenerInvestigadoresPorDependenciaAreaDeConocimiento(String nombreAreaConocimiento,
            Dependencia dependencia, String tipo_dependencia) {
        List a = null;
        try {
            a = personaDAO.obtenerInvestigadoresPorDependenciaAreaDeConocimiento(nombreAreaConocimiento, dependencia,
                    tipo_dependencia);
            System.out.println("devuelve la lista");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return a;
    }

    public List obtenerInvestigadoresPorAreaDeConocimiento(String nombreAreaConocimiento) {

        List a = null;
        try {
            // Set set_investigadores = new HashSet();

            // set_investigadores.addAll(personaDAO.obtenerInvestigadoresPorAreaDeConocimiento(nombreAreaConocimiento));
            // a = new ArrayList(set_investigadores);
            a = personaDAO.obtenerInvestigadoresPorAreaDeConocimiento(nombreAreaConocimiento);
            System.out.println("devuelve la lista");
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.println("no pudo");
        }
        return a;

    }

    public boolean seCruzanDependencia(Dependencia dependieciaInvestigador, Dependencia dependieciaConvocatoria) {
        List listaDependenciaConvocatoria = new Vector();
        listaDependenciaConvocatoria.add(dependieciaConvocatoria);
        dependenciaDAO.obtenerDependenciasHija(dependieciaConvocatoria, listaDependenciaConvocatoria);

        List listaDependenciaInvestigador = new Vector();
        listaDependenciaInvestigador.add(dependieciaInvestigador);
        dependenciaDAO.obtenerDependenciasHija(dependieciaInvestigador, listaDependenciaInvestigador);
        Set sDepedenciasConvocatoria = new HashSet(listaDependenciaConvocatoria);
        Set sDepedenciasInvestigador = new HashSet(listaDependenciaInvestigador);
        System.out.println("dependencias  de caonvocatoria ");
        for (Iterator itConvocatoria = sDepedenciasConvocatoria.iterator(); itConvocatoria.hasNext();) {

            Dependencia d = (Dependencia) itConvocatoria.next();
            System.out.println(d.getId());
            if (sDepedenciasInvestigador.contains(d))
                return true;
        }
        System.out.println("invest");
        for (Iterator itInvestigador = sDepedenciasInvestigador.iterator(); itInvestigador.hasNext();) {
            Dependencia d = (Dependencia) itInvestigador.next();
            System.out.println(d.getId());
            if (sDepedenciasConvocatoria.contains(d))
                return true;
        }
        return false;
    }

    public void setDependenciaDAO(IDependenciaDAO dependenciaDAO) {
        this.dependenciaDAO = dependenciaDAO;
    }

    public Estudiante obtenerEstudianteXPersona(Persona p) {
        return personaDAO.obtenerEstudianteXPersona(p);
    }

    public void insertarDatosEstudianteDePersona(Persona p) {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(p.getId());
        estudiante.setApellido1(p.getApellido1());
        estudiante.setApellido2(p.getApellido2());
        estudiante.setNombre1(p.getNombre1());
        estudiante.setNombre2(p.getNombre2());
        estudiante.setCiudadDomicilio(p.getCiudadDomicilio());
        estudiante.setFechaNacimiento(p.getFechaNacimiento());
        estudiante.setDireccion(p.getDireccion());
        estudiante.setEmail(p.getEmail());
        estudiante.setEstadoCivil(p.getEstadoCivil());
        estudiante.setGenero(p.getGenero());
        estudiante.setTelefono(p.getTelefono());
        estudiante.setCiudadNacimiento(p.getCiudadNacimiento());
        estudiante.setCiudadExpedicion(p.getCiudadExpedicion());
        Long c = personaDAO.obtenerIdCategoriaInvestigador(p.getId());
        if (c == null) {
            CategoriaInvestigador ci = new CategoriaInvestigador();
            ci.setId(new Long(CategoriaInvestigador.IDESTUDIANTE));
            estudiante.setCategoriaInvestigador(ci);
        } else {
            CategoriaInvestigador ci = new CategoriaInvestigador();
            ci.setId(c);
            estudiante.setCategoriaInvestigador(ci);
        }
        String esInterno = personaDAO.obtenerEsInterno(p.getId());
        if (esInterno == null) {
            estudiante.setInterno(Investigador.EXTERNO);
        } else {
            estudiante.setInterno(esInterno);
        }
        generalDAO.insertarObjeto(estudiante);
    }

    public void convertivirPersonaAInvestigadorExterno(Persona p, Investigador ie) {
        ie.setApellido1(p.getApellido1());
        ie.setApellido2(p.getApellido2());
        ie.setCiudadDomicilio(p.getCiudadDomicilio());
        ie.setCiudadExpedicion(p.getCiudadExpedicion());
        ie.setCiudadNacimiento(p.getCiudadNacimiento());
        ie.setDireccion(p.getDireccion());
        ie.setEmail(p.getEmail());
        ie.setEstadoCivil(p.getEstadoCivil());
        ie.setFechaNacimiento(p.getFechaNacimiento());
        ie.setGenero(p.getGenero());
        ie.setId(p.getId());
        ie.setNacionalidad(p.getNacionalidad());
        ie.setNombre1(p.getNombre1());
        ie.setNombre2(p.getNombre2());
        ie.setPaisOrigen(p.getPaisOrigen());
        ie.setProyectosAsesor(p.getProyectosAsesor());
        ie.setTelefono(p.getTelefono());
        ie.setCelular(p.getCelular());
        ie.setUid(p.getUid());
        ie.setProfesion(p.getProfesion());
        ie.setFechaTituloPregrado(p.getFechaTituloPregrado());
        ie.setPromedioPregrado(p.getPromedioPregrado());
        ie.setResumenHojaDeVida(p.getResumenHojaDeVida());
        ie.setFacultadPregrado(p.getFacultadPregrado());
    }

    public void insertaInterno(InvestigadorInterno ii) {
        personaDAO.insertaInterno(ii);
    }

    public void insertarExterno(InvestigadorExterno ie) {
        personaDAO.insertarExterno(ie);
    }

    public void insertarExternoContraseña(InvestigadorExterno ie) {
        personaDAO.insertarExternoContraseña(ie);
    }

    public void insertarInvestigador(Investigador i) {
        personaDAO.insertarInvestigador(i);
    }

    public List obtenerInvestigadoresPrincipales() {
        return personaDAO.obtenerInvestigadoresPrincipales();
    }

    public List obtenerEvaluadores() {
        return personaDAO.obtenerEvaluadores();
    }

    public List obtenerInvestigadoresProyectos() {
        return personaDAO.obtenerInvestigadoresProyectos();
    }

    public List obtenerInvestigadoresGrupos() {
        return personaDAO.obtenerInvestigadoresGrupos();
    }

    public InvestigadorExterno obtenerInvestigadorExternoClasificacionConocimiento(IdPersona id) {
        return personaDAO.obtenerInvestigadorExternoClasificacionConocimiento(id);
    }

    public boolean esInvestigadorExterno(IdPersona id) {
        return personaDAO.esInvestigadorExterno(id);
    }

    public boolean esInvestigadorInterno(IdPersona id) {
        return personaDAO.esInvestigadorInterno(id);
    }

    public void agregarRolPersona(IdPersona idPersona, Rol rol) {
        personaDAO.agregarRolPersona(idPersona, rol);
    }

    public void insertaEvaluadorCorreo(Proyecto p, TipoDocumento td, String contactado[]) {
        personaDAO.insertaEvaluadorCorreo(p, td, contactado);
    }

    public String insertaRespuestaEvaluadorCorreo(EvaluadorCorreo evalco) {

        mensajeError = "";
        try {
            if (evalco.getRespuesta().length() < 0 || evalco.getRespuesta().length() > 200) {
                mensajeError = "La respuesta no debe superar 200 caracteres";
                throw new Exception(mensajeError);
            }
            personaDAO.insertaRespuestaEvaluadorCorreo(evalco);
        } catch (Exception ex) {

        }
        return mensajeError;
    }

    public List obtenerEvaluadoresCorreoProyecto(Proyecto p) {

        return personaDAO.obtenerEvaluadoresCorreoProyecto(p);
    }

    public List obtenerlistaProyectosEvaluador(Proyecto p) {
        return personaDAO.obtenerlistaProyectosEvaluador(p);
    }

    public String guardarRol(Rol rol) {

        mensajeRol = "";
        try {
            if (rol.getNombre() == null || rol.getNombre().equals("")) {
                mensajeRol = "Debe ingresar el nombre del Rol a ingresar";
                throw new Exception(mensajeRol);
            }
            if (rol.getId() == null || rol.getId().equals("")) {
                mensajeRol = "Debe ingresar la Abreviatura del Rol a ingresar";
                throw new Exception(mensajeRol);
            }
            try {
                personaDAO.guardarRol(rol);
            } catch (DataIntegrityViolationException ex) {
                if (ex.getMessage().indexOf("PK_HER_ROLES") > 0)
                    mensajeRol = "El rol ha ingresar ya existe";
                else
                    mensajeRol = "Ocurrio un error inesperado al guardar el Rol";
            } catch (Exception e) {
                mensajeRol = "Ocurrio un error inesperado al guardar el Rol";
            }
        } catch (Exception ex) {

        }
        return mensajeRol;
    }

    public List obtenerRols(IdPersona id) {
        return personaDAO.obtenerRols(id);
    }

    public List obtenerTodosLosRoles() {
        return personaDAO.obtenerTodosLosRoles();
    }

    public void crearRoles(PersonaRol perRol) {

        personaDAO.crearRoles(perRol);
    }

    public void guardarInvInterno(InvestigadorInterno interno) {
        personaDAO.guardarInvInterno(interno);
    }

    public Persona obtenerPosibleEvaluador(IdPersona id) {
        return personaDAO.obtenerPosibleEvaluador(id);
    }

    public String obtenerUltimoConsecutivo() {
        return personaDAO.obtenerUltimoConsecutivo();
    }

    public void borrarRoles(PersonaRol perRol) {
        personaDAO.borrarRoles(perRol);
    }

    public List buscarRoles(String documento) {
        return personaDAO.buscarRoles(documento);
    }

    public List buscarRoles2(String documento, String tipoDocumento) {
        return personaDAO.buscarRoles2(documento, tipoDocumento);
    }

    public String insertarNuevoEvaluadorExterno(PosibleEvaluador posv, String consecutivo) {
        mensajeEvaluador = "";
        try {
            if (posv.getDocumento() == null || posv.getDocumento().equals("")) {
                mensajeEvaluador = "Debe ingresar el Documento del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getNombre() == null || posv.getNombre().equals("")) {
                mensajeEvaluador = "Debe ingresar el Nombre del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getApellido1() == null || posv.getApellido1().equals("")) {
                mensajeEvaluador = "Debe ingresar el Primer Apellido del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getEmail() == null || posv.getEmail().equals("")) {
                mensajeEvaluador = "Debe ingresar el E-mail del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            personaDAO.insertarNuevoEvaluadorExterno(posv, consecutivo);
        } catch (Exception ex) {

        }
        return mensajeEvaluador;
    }

    public String buscarUltimoConsecutivo() {
        return personaDAO.buscarUltimoConsecutivo();
    }

    public List obtenerDocenteinvestigador(String persona) {
        return personaDAO.obtenerDocenteinvestigador(persona);
    }

    public String insertarNuevoInvestigador(Investigador inv) {
        mensajeInvestigador = null;

        try {
            personaDAO.insertarNuevoInvestigador(inv);
        } catch (Exception ex) {
            mensajeInvestigador = ex.getMessage();
            ex.printStackTrace();
        }
        return mensajeInvestigador;
    }

    public void insertarNuevaPersona(Persona person) {
        personaDAO.insertarNuevaPersona(person);
    }

    public boolean insertarNuevaPersonaDatosCompletos(Persona person) {
        return personaDAO.insertarNuevaPersonaDatosCompletos(person);
    }

    public boolean insertarNuevaPersonaDatosBasicos(Persona person) {
        return personaDAO.insertarNuevaPersonaDatosBasicos(person);
    }
    
    public boolean insertarNuevaPersonaDatosBasicosPI(Persona person) {
        return personaDAO.insertarNuevaPersonaDatosBasicosPI(person);
    }

    public boolean insertarNuevaPersonaDatosBasicosFuncytca(Persona person) {
        return personaDAO.insertarNuevaPersonaDatosBasicosFuncytca(person);
    }

    public boolean actualizarPersonaFuncytca(Persona person) {
        return personaDAO.actualizarPersonaFuncytca(person);
    }

    public boolean actualizarPersonaDatosBasicos(Persona person) {
        return personaDAO.actualizarPersonaDatosBasicos(person);
    }

    public boolean actualizarPersonaFuncytcaCompleto(Persona person) {
        return personaDAO.actualizarPersonaFuncytcaCompleto(person);
    }

    public void insertarNuevoInvestigadorExterno(InvestigadorExterno externo) {
        personaDAO.insertarNuevoInvestigadorExterno(externo);
    }

    public List obtenerListaCoordinadores(String persona) {
        return personaDAO.obtenerListaCoordinadores(persona);
    }
    
    public List obtenerListaCoordinadoresEditorial(String persona) {
        return personaDAO.obtenerListaCoordinadoresEditorial(persona);
    }

    public boolean validarInvestigadorModalidad(Long idModalidad, IdPersona idPersona) {
        return personaDAO.validarInvestigadorModalidad(idModalidad, idPersona);
    }

    public String actualizarEvaluadorExterno(PosibleEvaluador posv) {
        mensajeEvaluador = "";
        try {
            if (posv.getDocumento() == null || posv.getDocumento().equals("")) {
                mensajeEvaluador = "Debe ingresar el Documento del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getNombre() == null || posv.getNombre().equals("")) {
                mensajeEvaluador = "Debe ingresar el Nombre del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getApellido1() == null || posv.getApellido1().equals("")) {
                mensajeEvaluador = "Debe ingresar el Primer Apellido del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            if (posv.getEmail() == null || posv.getEmail().equals("")) {
                mensajeEvaluador = "Debe ingresar el E-mail del evaluador";
                throw new Exception(mensajeEvaluador);
            }
            personaDAO.actualizarEvaluadorExterno(posv);
        } catch (Exception ex) {

        }
        return mensajeEvaluador;
    }

    public void agregarClasificacionConocimientoInvestigador(IdPersona idPersona, String idClasificacionConocimiento) {
        personaDAO.agregarClasificacionConocimientoInvestigador(idPersona, idClasificacionConocimiento);
    }

    public List obtenerListaCoordinadoresAsesor(IdPersona idAsesor) {
        return personaDAO.obtenerListaCoordinadoresAsesor(idAsesor);
    }
    
    public List obtenerListaCoordinadoresAsesorEditorial(IdPersona idAsesor) {
        return personaDAO.obtenerListaCoordinadoresAsesorEditorial(idAsesor);
    }

    public List obtenerListaDependenciasAsesor(IdPersona idAsesor) {
        return personaDAO.obtenerListaDependenciasAsesor(idAsesor);
    }
    
    public List obtenerListaDependenciasAsesorEditorial(IdPersona idAsesor) {
        return personaDAO.obtenerListaDependenciasAsesorEditorial(idAsesor);
    }

    public List obtenerListaCoordinadoresNoAsignados() {
        return personaDAO.obtenerListaCoordinadoresNoAsignados();
    }

    public List obtenerListaCoordinadoresNoAsignadosAsesor(IdPersona asesor) {
        return personaDAO.obtenerListaCoordinadoresNoAsignadosAsesor(asesor);

    }

    public void insertarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
        personaDAO.insertarCoordinadorAsesor(idAsesor, idCoordinador, idDependencia);
    }
    
    public void insertarCoordinadorAsesorEditorial(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
        personaDAO.insertarCoordinadorAsesorEditorial(idAsesor, idCoordinador, idDependencia);
    }

    public void insertarDependenciaAsesor(IdPersona idAsesor, String idDependencia) {
        personaDAO.insertarDependenciaAsesor(idAsesor, idDependencia);
    }

    public void eliminarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
        personaDAO.eliminarCoordinadorAsesor(idAsesor, idCoordinador, idDependencia);
    }

    public void eliminarDependenciaAsesor(IdPersona idAsesor, String idDependencia) {

        personaDAO.eliminarDependenciaAsesor(idAsesor, idDependencia);
    }

    public List obtenerPuntajesInvestigador(IdPersona idInvestigador) {
        return personaDAO.obtenerPuntajesInvestigador(idInvestigador);
    }

    public List<Persona> obtenerInvestigadoresBuscador(String sql) throws DataAccessException {
        return personaDAO.obtenerInvestigadoresBuscador(sql);
    }

    public List obtenerRolesDependencia(String dep, String rol) {
        return personaDAO.obtenerRolesDependencia(dep, rol);
    }

    public List obtenerRolesSolicitud() {
        return personaDAO.obtenerRolesSolicitud();
    }

	@Override
	public List<LineaInvestigacion> obtenerLineasInvestigacionEvaluadorExterno(IdPersona id) {
		// TODO Auto-generated method stub
		return personaDAO.obtenerLineasInvestigacionEvaluadorExterno(id);
	}

	@Override
	public void insertarNuevoEvaluador(Evaluador e) {
		// TODO Auto-generated method stub
		personaDAO.insertarNuevoEvaluador(e);
	}
	
	public void guardarLineasInvestigacionEvaluadorExterno(List<LineaInvestigacion> list, IdPersona id) {
		// TODO Auto-generated method stub
		personaDAO.guardarLineasInvestigacionEvaluadorExterno(list, id);
	}

	@Override
	public void actualizarEvaluador(Evaluador evaluador) {
		// TODO Auto-generated method stub
		personaDAO.actualizarEvaluador(evaluador);
	}
	
	public void actualizarFechaVencimientoRol(IdPersona persona, Rol rol, Date fechaVencimiento) {
		// TODO Auto-generated method stub
		personaDAO.actualizarFechaVencimientoRol(persona, rol, fechaVencimiento);
	}

}
