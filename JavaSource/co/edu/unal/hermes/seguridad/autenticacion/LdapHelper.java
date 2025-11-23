package co.edu.unal.hermes.seguridad.autenticacion;

import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import co.edu.unal.hermes.modelo.ParametroMaestro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.servicioGeneral.IServicioGeneral;
import co.edu.unal.hermes.modelo.servicioGeneral.ServicioGeneral;
import co.edu.unal.hermes.modelo.servicioPersona.IServicioPersona;

public class LdapHelper {

	private static String factory = "com.sun.jndi.ldap.LdapCtxFactory";
	private static String secureServer = "3.214.137.153";//"ldapmcloud02.unal.edu.co"; //"chingaza01.unal.edu.co"; "10.31.3.13";
	private static int securePort = 636;//389;
	private static String rootdn = "o=unal.edu.co";
	private static String sedes = "o=bogota|o=manizales|o=medellin|o=arauca|o=leticia|o=palmira|o=san andres";
	private static DirContext initialDirCtx;
	private static SearchControls constraints = new SearchControls();

	public LdapHelper() {
		
	}
	
	public Usuario autenticar(String user, String password) {
		// PATH DE DESARROLLO: Cambiar ruta a version de JAVA que use el equipo.
//		String keystorePath = "C:/Program Files/Java/jre1.8.0_60/lib/security/cacerts";
		
		// PATH DE PRODUCCIÓN: Habilitar esta ruta cuando se ponga en los servidores.
		String keystorePath = "/usr/jdk/instances/jdk1.6.0/jre/lib/security/cacerts";
		
		// PATH DE PRODUCCIÓN: Habilitar esta ruta cuando se ponga en los servidores.
		//String keystorePath = "/usr/lib/jvm/java-1.8.0-amazon-corretto.x86_64/jre/lib/security/cacerts";
		
		System.setProperty("javax.net.ssl.keyStore", keystorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		Usuario usuario = null;
		StringTokenizer st = new StringTokenizer(sedes, "|");
		while (usuario == null && st.hasMoreTokens()) {
			String sede = st.nextToken();
			String dn = construirDn_LDAPS(user);
			try {
				initialDirCtx = searchDirectoryContext_LDAPS(password, dn);
				String[] attrIDs = { "uid", "cn", "sn", "employeeType", "employeeNumber" };
				Attributes matchAttrs = new BasicAttributes(true);
				matchAttrs.put(new BasicAttribute("uid", user));
				NamingEnumeration answer = search(sede, matchAttrs, attrIDs);
				while (answer.hasMore()) {
					usuario = new Usuario();
					SearchResult sr = (SearchResult) answer.next();
					Attributes atributos = sr.getAttributes();
					Attribute uid = atributos.get("uid");
					Attribute apellidos = atributos.get("sn");
					Attribute rol = atributos.get("employeeType");
					Attribute cedula = atributos.get("employeeNumber");
					usuario.setUid((String) uid.get());
					usuario.setCedula((String) cedula.get());
					System.out.println("Documento LDAP: " + cedula);
					System.out.println("uid LDAP: " + uid);
				}
				System.out.println("Conexion Exitosa LDAPS: " + secureServer);
			} catch (Exception e) {
				System.out.println("Error LDAPS: " + secureServer);
				e.printStackTrace();
			}
		}
		return usuario;
	}

	public static DirContext searchDirectoryContext_LDAPS(String password, String dn) throws NamingException {
		ApplicationContext actx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		IServicioGeneral servicioGeneral = (IServicioGeneral) actx.getBean("servicioGeneral");
        ParametroMaestro server = servicioGeneral.obtenerParametroPorNombre(ParametroMaestro.LDAP_SERVER);
        ParametroMaestro port = servicioGeneral.obtenerParametroPorNombre(ParametroMaestro.LDAP_PORT);
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
		env.put(Context.PROVIDER_URL, "ldaps://" + server.getValor() + ":" + port.getValor());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);
		DirContext ctx = new InitialDirContext(env);
		ctx = (DirContext) ctx.lookup(rootdn);
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		return ctx;
	}

	public NamingEnumeration search(String org, Attributes matchAttrs, String[] returnID) throws NamingException {
		NamingEnumeration answer = null;
		answer = initialDirCtx.search("ou=People", matchAttrs, returnID);
		if (answer != null && answer.hasMore()) {
			return answer;
		} else {
			answer = initialDirCtx.search("ou=People," + org, matchAttrs, returnID);
			if (answer != null) {
				return answer;
			}
		}
		return null;
	}

	public NamingEnumeration search(String org, String atributos, SearchControls controles) throws NamingException {
		NamingEnumeration answer = null;
		answer = initialDirCtx.search("ou=People", atributos, controles);
		return answer;
	}

	private String construirDn_LDAPS(String user) {
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
			// env.put(Context.PROVIDER_URL, "ldap://" + servidor + ":" + port);
			ApplicationContext actx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			IServicioGeneral servicioGeneral = (IServicioGeneral) actx.getBean("servicioGeneral");
			ParametroMaestro server = servicioGeneral.obtenerParametroPorNombre(ParametroMaestro.LDAP_SERVER);
			ParametroMaestro port = servicioGeneral.obtenerParametroPorNombre(ParametroMaestro.LDAP_PORT);
			
			env.put(Context.PROVIDER_URL, "ldaps://" + server.getValor() + ":" + port.getValor());
			DirContext dc;
			dc = new InitialDirContext(env);
			String filter = "(uid=" + user + ")";
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration answer = dc.search(rootdn, filter, ctrl);
			String dn;
			if (answer.hasMore()) {
				SearchResult result = (SearchResult) answer.next();
				dn = result.getNameInNamespace();
			} else {
				dn = null;
			}
			answer.close();
			return dn;
		} catch (NamingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
