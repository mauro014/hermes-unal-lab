package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAprobacionMovilidadConvocatoriaSede extends ManejadorBase
{

	private boolean facultadTieneConvocatoria = false;

	private List listaMovilidadesModUno;
	private List listaMovilidadesModDos;
	private List listaMovilidadesModTres;
	private List listaMovilidadesModCuatro;

	private MovilidadEstudiantesPosgrado movilidadModUnoSeleccionada;
	private MovilidadDocentesExterior movilidadModDosSeleccionada;
	private MovilidadVisitanteExterior movilidadModTresSeleccionada;
	private MovilidadDocentesExterior movilidadModCuatroSeleccionada;

	private ArchivoMovilidadEP ArchivoMovilidadModUnoSeleccionada;
	private ArchivoMovilidadDE archivoMovilidadModDosSeleccionada;
	private ArchivoMovilidadVE archivoMovilidadModTresSeleccionada;
	private ArchivoMovilidadDE archivoMovilidadModCuatroSeleccionada;
	
	CorreoPlantilla correoActual = new CorreoPlantilla();	
	String cuerpoCorreo = "";

	public ManejadorAprobacionMovilidadConvocatoriaSede()
	{
		try
		{
			Persona personaActual = (Persona) sesion.getAttribute("persona");
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

			String sqlBuscaConvocatoria = "select #id e.id from Convocatoria e where e.tipo.id = 'CM' and e.dependencia.sede.id = " + ii.getDependencia2().getSede().getId() + "";

			List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);

			System.out.println("tamaño lista convocatorias " + listConvs.size());

			if (listConvs != null && listConvs.size() > 0)
			{
				facultadTieneConvocatoria = true;
				listaMovilidadesModUno = new ArrayList();
				listaMovilidadesModDos = new ArrayList();
				listaMovilidadesModTres = new ArrayList();
				listaMovilidadesModCuatro = new ArrayList();

				cargarListasMovilidades();
				
				System.out.println(listaMovilidadesModUno.size());
				System.out.println(listaMovilidadesModDos.size());
				System.out.println(listaMovilidadesModTres.size());
				System.out.println(listaMovilidadesModCuatro.size());
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "La sede no tiene convocatoria de movilidad", ""));
				facultadTieneConvocatoria = false;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("PAILAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		}

	}

	public void cargarListasMovilidades()
	{
		Persona persona = new Persona();
		persona = (Persona) sesion.getAttribute("persona");
		Dependencia dependencia = null;

		try
		{
			persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
			InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
			dependencia = servicioDependencia.obtenerDependencia2(investigadorInterno.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String sqlMovUno = "select e from MovilidadEstudiantesPosgrado e where ";
		
		listaMovilidadesModUno = servicioMovilidad.obtenerMovilidadesXTipoConvFacultad("CF_MOV1", persona, dependencia);
		listaMovilidadesModDos = servicioMovilidad.obtenerMovilidadesXTipoConvFacultad("CF_MOV2", persona, dependencia);
 		listaMovilidadesModTres = servicioMovilidad.obtenerMovilidadesXTipoConvFacultad("CF_MOV3", persona, dependencia);
		listaMovilidadesModCuatro = servicioMovilidad.obtenerMovilidadesXTipoConvFacultad("CF_MOV4", persona, dependencia);

	}
	
	public void descargarDocumentoEstPosgrado() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen4");	
		Long idArchivo = Long.valueOf((String) o);
		o= (Object) map.get("movilidadModUno");
		Long idMEP = Long.valueOf((String) o);
		ArchivoMovilidadEP archMEPSel = null;
		MovilidadEstudiantesPosgrado mdepSel = null;
		for(Object o2:listaMovilidadesModUno){
			MovilidadEstudiantesPosgrado me = (MovilidadEstudiantesPosgrado) o2;
			Long idMep2 = me.getId();
			if(idMep2.equals(idMEP)){
				mdepSel = me;
				break;
			}
		}
		if(mdepSel != null){
			Iterator it = mdepSel.getArchivos().iterator();
			while(it.hasNext()){
				ArchivoMovilidadEP amde = (ArchivoMovilidadEP)it.next();
				if(amde.getId().equals(idArchivo)){
					archMEPSel = amde;
					break;
				}
			}
		}
		if(archMEPSel != null){
			ArchivoMovilidadEP archivo = (ArchivoMovilidadEP) archMEPSel;
		
	
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
					if (!ctx.getResponseComplete()) {
						HttpServletResponse response = (HttpServletResponse) ctx
								.getExternalContext().getResponse();
						response.setContentType("text/plain");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"" + archivo.getNombre() 
								//"attachment;filename=\"" + "Documento.pdf"
										+ "\"");
						ServletOutputStream out = response.getOutputStream();
						out.write(archivo.getBytes());
						out.flush();
						ctx.responseComplete();
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void  imprimirMovilidadPosgrado(){
		FacesContext context = FacesContext.getCurrentInstance();
		Long id = movilidadModUnoSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		if(movilidadModUnoSeleccionada.getTipoMovilidad().getId().equals("CF_MOV1"))
			r.setNombreReporte("/movilidad/DApoyoEstudiantesPosgrado_CF_MOV1_SEDE");
		else r.setNombreReporte("/movilidad/DApoyoEstudiantesPosgradoMov4_CF_MOV1_SEDE");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			//System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}
	
	public String noAprobarMovilidadPosgrado() {

		MovilidadEstudiantesPosgrado mov = new MovilidadEstudiantesPosgrado();
		MovilidadEstudiantesPosgrado movAux = new MovilidadEstudiantesPosgrado();

		Long id =  movilidadModUnoSeleccionada.getId();
		movAux=movilidadModUnoSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadEstudiantesPosgrado where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}
		
		mov= (MovilidadEstudiantesPosgrado)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setAprobacion("NO");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		//servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);
		
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		
		correoActual = cargarPlantilla(68);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public String editarCorreo(Persona personaAux, String tipo, Long id, String comFac) {
		comFac = eliminarCaracterSinReplace("$", comFac);
		try {
			String coinvNombre = "";
	
			String correo = correoActual.getCuerpo();
			
			String investigador = "";
	
				investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " "
						+ personaAux.getApellido2();
				correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
	

			correo = correo.replaceAll("<<IDMOVILIDAD>>", id.toString());
			correo = correo.replaceAll("<<TIPO>>", tipo);
			
			correo = correo.replaceAll("<<OBSERVACION>>", comFac);
			
			cuerpoCorreo = correo;
			
			//cuerpoCorreo2 = investigador + "		"  + coinvNombre;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}
	
	
	public String aprobarMovilidadPosgrado() {

		MovilidadEstudiantesPosgrado mov = new MovilidadEstudiantesPosgrado();
		MovilidadEstudiantesPosgrado movAux = new MovilidadEstudiantesPosgrado();
		
		Long id = (movilidadModUnoSeleccionada).getId();
	
		movAux=movilidadModUnoSeleccionada;
		
		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadEstudiantesPosgrado where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}
		
		mov= (MovilidadEstudiantesPosgrado)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setAprobacion("SI");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		servicioGeneral.guardarObjeto(mov);
		
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		
		correoActual = cargarPlantilla(66);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		
		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();
		
		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();
		
		personaEnvio = servicioPersona
		.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia
		.obtenerDependencia(investigadorInterno.getId());
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public void descargarDocumentoEvento() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen");	
		Long idArchivo = Long.valueOf((String) o);
		o= (Object) map.get("movilidadModDos");			
		Long idME = Long.valueOf((String) o);
		ArchivoMovilidadDE archMDESel = null;
		MovilidadDocentesExterior mdeSel = null;
		for(Object o2:listaMovilidadesModDos){
			MovilidadDocentesExterior me = (MovilidadDocentesExterior)o2;
			Long idMe2 = me.getId();
			if(idMe2.equals(idME)){
				mdeSel = me;
				break;
			}
		}
		if(mdeSel != null){
			Iterator it = mdeSel.getArchivos().iterator();
			while(it.hasNext()){
				ArchivoMovilidadDE amde = (ArchivoMovilidadDE)it.next();
				if(amde.getId().equals(idArchivo)){
					archMDESel = amde;
					break;
				}
			}
		}
		if(archMDESel != null){

			ArchivoMovilidadDE archivo = archMDESel;			
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
					if (!ctx.getResponseComplete()) {
						HttpServletResponse response = (HttpServletResponse) ctx
								.getExternalContext().getResponse();
						response.setContentType("text/plain");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"" + archivo.getNombre() 
						
										+ "\"");
						ServletOutputStream out = response.getOutputStream();
						out.write(archivo.getBytes());
						out.flush();
						ctx.responseComplete();
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void imprimirMovilidadEvento() {
		Long id =  movilidadModDosSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		r.setNombreReporte("/movilidad/BMovilidadDocentesEventos_CF_MOV2_SEDE");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			//System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}
	
	public String noAprobarMovilidadEvento() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();
		
		Long id = ((MovilidadDocentesExterior) (movilidadModDosSeleccionada))
				.getId();
		
		movAux=(MovilidadDocentesExterior) (movilidadModDosSeleccionada);
		
						

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		
		personaActual = (Persona) sesion.getAttribute("persona");
		mov= (MovilidadDocentesExterior)listaMovilidad.get(0);
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setAprobacion("NO");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		
		
		servicioGeneral.guardarObjeto(mov);
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		correoActual = cargarPlantilla(68);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public String aprobarMovilidadEventos() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();
		
		Long id = ((MovilidadDocentesExterior) (movilidadModDosSeleccionada))
				.getId();
		
		movAux=(MovilidadDocentesExterior) (movilidadModDosSeleccionada);
	

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		
		mov= (MovilidadDocentesExterior)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setAprobacion("SI");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		servicioGeneral.guardarObjeto(mov);
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		correoActual = cargarPlantilla(66);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(), mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
			
		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();
		
		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();
		
		personaEnvio = servicioPersona
		.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia
		.obtenerDependencia(investigadorInterno.getId());
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public void descargarDocumentoVisitante() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen1");	
		Long idArchivo = Long.valueOf((String) o);
		o= (Object) map.get("movilidadModTres");			
		Long idMV = Long.valueOf((String) o);
		ArchivoMovilidadVE archMVSel = null;
		MovilidadVisitanteExterior mveSel = null;
		
		for(Object o2:listaMovilidadesModTres){
			MovilidadVisitanteExterior mve = (MovilidadVisitanteExterior)o2;
			Long idMve2 = mve.getId();
			if(idMve2.equals(idMV)){
				mveSel = mve;
				break;
			}
		}

		if(mveSel != null){
			Iterator it = mveSel.getArchivos().iterator();
			while(it.hasNext()){
				ArchivoMovilidadVE amde = (ArchivoMovilidadVE)it.next();
				if(amde.getId().equals(idArchivo)){
					archMVSel = amde;
					break;
				}
			}
		}

		if(archMVSel != null){

			ArchivoMovilidadVE archivo = archMVSel;
		
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
					if (!ctx.getResponseComplete()) {
						HttpServletResponse response = (HttpServletResponse) ctx
								.getExternalContext().getResponse();
						response.setContentType("text/plain");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"" + archivo.getNombre() 
								//"attachment;filename=\"" + "Documento.pdf"
										+ "\"");
						ServletOutputStream out = response.getOutputStream();
						out.write(archivo.getBytes());
						out.flush();
						ctx.responseComplete();
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void imprimirMovilidadVisitante() {
		Long id = movilidadModTresSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		r.setNombreReporte("/movilidad/A1MovilidadVisitantesExt_CF_MOV3_SEDE");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			//System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}
	
	public String noAprobarMovilidadVisitante() {

		MovilidadVisitanteExterior mov = new MovilidadVisitanteExterior();
		MovilidadVisitanteExterior movAux = new MovilidadVisitanteExterior();

		
		Long id = ((MovilidadVisitanteExterior) (movilidadModTresSeleccionada))
				.getId();
		movAux=(MovilidadVisitanteExterior) (movilidadModTresSeleccionada);	
		

		

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadVisitanteExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}
		
		mov= (MovilidadVisitanteExterior)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setAprobacion("NO");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		//servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		correoActual = cargarPlantilla(68);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public String aprobarMovilidadVisitante() {
		MovilidadVisitanteExterior mov = new MovilidadVisitanteExterior();
		MovilidadVisitanteExterior movAux = new MovilidadVisitanteExterior();
		
		Long id = movilidadModTresSeleccionada.getId();
		movAux= movilidadModTresSeleccionada ;				

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadVisitanteExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}
		
		mov= (MovilidadVisitanteExterior)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setAprobacion("SI");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		//servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);
		
	
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		
		correoActual = cargarPlantilla(66);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
				
		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();
		
		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();
		
		personaEnvio = servicioPersona
		.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia
		.obtenerDependencia(investigadorInterno.getId());				
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	
	public void descargarDocumentoEventoEsp() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen3");	
		Long idArchivo = Long.valueOf((String) o);
		o= (Object) map.get("movilidadModCuatro");			
		Long idME = Long.valueOf((String) o);
		ArchivoMovilidadDE archMDESel = null;
		MovilidadDocentesExterior mdeSel = null;
		for(Object o2:listaMovilidadesModCuatro){
			MovilidadDocentesExterior me = (MovilidadDocentesExterior)o2;
			Long idMe2 = me.getId();
			if(idMe2.equals(idME)){
				mdeSel = me;
				break;
			}
		}
		if(mdeSel != null){
			Iterator it = mdeSel.getArchivos().iterator();
			while(it.hasNext()){
				ArchivoMovilidadDE amde = (ArchivoMovilidadDE)it.next();
				if(amde.getId().equals(idArchivo)){
					archMDESel = amde;
					break;
				}
			}
		}
		if(archMDESel != null){

			ArchivoMovilidadDE archivo = archMDESel;			
			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
					if (!ctx.getResponseComplete()) {
						HttpServletResponse response = (HttpServletResponse) ctx
								.getExternalContext().getResponse();
						response.setContentType("text/plain");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"" + archivo.getNombre() 
						
										+ "\"");
						ServletOutputStream out = response.getOutputStream();
						out.write(archivo.getBytes());
						out.flush();
						ctx.responseComplete();
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void imprimirMovilidadEventoEsp() {
		Long id =  movilidadModCuatroSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		r.setNombreReporte("/movilidad/BMovilidadDocentesEventos_CF_MOV4_SEDE");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			//System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}
	
	public String noAprobarMovilidadEventoEsp() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();
		
		Long id = ((MovilidadDocentesExterior) (movilidadModCuatroSeleccionada))
				.getId();
		
		movAux=(MovilidadDocentesExterior) (movilidadModCuatroSeleccionada);
		
						

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		
		personaActual = (Persona) sesion.getAttribute("persona");
		mov= (MovilidadDocentesExterior)listaMovilidad.get(0);
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setAprobacion("NO");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		
		
		servicioGeneral.guardarObjeto(mov);
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		correoActual = cargarPlantilla(68);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(),mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}
	
	public String aprobarMovilidadEventosEsp() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();
		
		Long id = ((MovilidadDocentesExterior) (movilidadModCuatroSeleccionada))
				.getId();
		
		movAux=(MovilidadDocentesExterior) (movilidadModCuatroSeleccionada);
	

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id ='"
						+ id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		
		mov= (MovilidadDocentesExterior)listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null ){
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setAprobacion("SI");
		mov.setValorAprobadoFacultad(movAux.getValorAprobadoFacultad());
		mov.setComentariosFac(movAux.getComentariosFac());
		servicioGeneral.guardarObjeto(mov);
		
		String dirCorreoConfirmacion = personaActual.getEmail();
		
		correoActual = cargarPlantilla(66);//42
		editarCorreo(mov.getPersonaInv(),mov.getTipoMovilidad().getNombre(), mov.getId(),mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
			
		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();
		
		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();
		
		personaEnvio = servicioPersona
		.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia
		.obtenerDependencia(investigadorInterno.getId());
		
		sesion.removeAttribute("ManejadorAprobacionMovilidadConvocatoriaSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionConvFacultad";
	}

	public boolean isFacultadTieneConvocatoria()
	{
		return facultadTieneConvocatoria;
	}

	public void setFacultadTieneConvocatoria(boolean facultadTieneConvocatoria)
	{
		this.facultadTieneConvocatoria = facultadTieneConvocatoria;
	}

	public MovilidadEstudiantesPosgrado getMovilidadModUnoSeleccionada()
	{
		return movilidadModUnoSeleccionada;
	}

	public void setMovilidadModUnoSeleccionada(MovilidadEstudiantesPosgrado movilidadModUnoSeleccionada)
	{
		this.movilidadModUnoSeleccionada = movilidadModUnoSeleccionada;
	}

	public MovilidadDocentesExterior getMovilidadModDosSeleccionada()
	{
		return movilidadModDosSeleccionada;
	}

	public void setMovilidadModDosSeleccionada(MovilidadDocentesExterior movilidadModDosSeleccionada)
	{
		this.movilidadModDosSeleccionada = movilidadModDosSeleccionada;
	}

	public MovilidadVisitanteExterior getMovilidadModTresSeleccionada()
	{
		return movilidadModTresSeleccionada;
	}

	public void setMovilidadModTresSeleccionada(MovilidadVisitanteExterior movilidadModTresSeleccionada)
	{
		this.movilidadModTresSeleccionada = movilidadModTresSeleccionada;
	}

	public MovilidadDocentesExterior getMovilidadModCuatroSeleccionada()
	{
		return movilidadModCuatroSeleccionada;
	}

	public void setMovilidadModCuatroSeleccionada(MovilidadDocentesExterior movilidadModCuatroSeleccionada)
	{
		this.movilidadModCuatroSeleccionada = movilidadModCuatroSeleccionada;
	}

	public ArchivoMovilidadEP getArchivoMovilidadModUnoSeleccionada()
	{
		return ArchivoMovilidadModUnoSeleccionada;
	}

	public void setArchivoMovilidadModUnoSeleccionada(ArchivoMovilidadEP archivoMovilidadModUnoSeleccionada)
	{
		ArchivoMovilidadModUnoSeleccionada = archivoMovilidadModUnoSeleccionada;
	}

	public ArchivoMovilidadDE getArchivoMovilidadModDosSeleccionada()
	{
		return archivoMovilidadModDosSeleccionada;
	}

	public void setArchivoMovilidadModDosSeleccionada(ArchivoMovilidadDE archivoMovilidadModDosSeleccionada)
	{
		this.archivoMovilidadModDosSeleccionada = archivoMovilidadModDosSeleccionada;
	}

	public ArchivoMovilidadVE getArchivoMovilidadModTresSeleccionada()
	{
		return archivoMovilidadModTresSeleccionada;
	}

	public void setArchivoMovilidadModTresSeleccionada(ArchivoMovilidadVE archivoMovilidadModTresSeleccionada)
	{
		this.archivoMovilidadModTresSeleccionada = archivoMovilidadModTresSeleccionada;
	}

	public ArchivoMovilidadDE getArchivoMovilidadModCuatroSeleccionada()
	{
		return archivoMovilidadModCuatroSeleccionada;
	}

	public void setArchivoMovilidadModCuatroSeleccionada(ArchivoMovilidadDE archivoMovilidadModCuatroSeleccionada)
	{
		this.archivoMovilidadModCuatroSeleccionada = archivoMovilidadModCuatroSeleccionada;
	}

	public List getListaMovilidadesModUno()
	{
		return listaMovilidadesModUno;
	}

	public void setListaMovilidadesModUno(List listaMovilidadesModUno)
	{
		this.listaMovilidadesModUno = listaMovilidadesModUno;
	}

	public List getListaMovilidadesModDos()
	{
		return listaMovilidadesModDos;
	}

	public void setListaMovilidadesModDos(List listaMovilidadesModDos)
	{
		this.listaMovilidadesModDos = listaMovilidadesModDos;
	}

	public List getListaMovilidadesModTres()
	{
		return listaMovilidadesModTres;
	}

	public void setListaMovilidadesModTres(List listaMovilidadesModTres)
	{
		this.listaMovilidadesModTres = listaMovilidadesModTres;
	}

	public List getListaMovilidadesModCuatro()
	{
		return listaMovilidadesModCuatro;
	}

	public void setListaMovilidadesModCuatro(List listaMovilidadesModCuatro)
	{
		this.listaMovilidadesModCuatro = listaMovilidadesModCuatro;
	}

	public CorreoPlantilla getCorreoActual()
	{
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual)
	{
		this.correoActual = correoActual;
	}

	public String getCuerpoCorreo()
	{
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo)
	{
		this.cuerpoCorreo = cuerpoCorreo;
	}

}
