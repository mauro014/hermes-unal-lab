/**
 * @author  Ing Juan Pablo Duque García
 */

package co.edu.unal.hermes.vista.correos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.CorreoBoletinDetalle;
import co.edu.unal.hermes.modelo.CorreoPersonaBoletin;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CorreoTipoPersonaBoletin;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCorreoBoletin extends ManejadorBase {

	private final static String RUTA_ADJUNTO = "/pages/Correos/adjuntos/";
	private UploadedFile archivo;
	private Correo correo;
	private String cedula = "";
	private String resultadoEnvio = "";
	private String asuntoBoletin = "";
	private String cuerpoBoletin = "";
	private String dirPrueba = "";
	private CorreoPersonaBoletin correoPersonaBoletin;
	private String tipoCorreoPersona;
	private String tipoCorreoSede;
	private SelectItem[] tipoCorreoPersonaItem;
	private SelectItem[] tipoCorreoSedeItem;
	private String seleccionTipoUsuarioEnvio;
	private String nombreBoton = "Enviar Boletín";
	private String progresoEnvio ="";
	private List listaTipoBoletin;
	private List listaTipoBoletinAux;
	private Date fechaActual;
	private String envioProfesores="";
	private String envioPosgrado="";
	private String envioAdministrativos="";
	private String envioPregrado="";
	private String envioOtros="";
	
	private String[] descripcionMes = { "enero", "febrero", "marzo", "abril",
			"mayo", "junio", "julio", "agosto", "septiembre", "octubre",
			"noviembre", "diciembre" };
	
	private Long totalListaOtros = 0L;
	private Long totalSuscritos = 0L;
		
	
	public ManejadorCorreoBoletin() {
		super();
		listaTipoBoletin =  new ArrayList();
		correo = new Correo();
		int k=0;
		cargarCorreoInicial();
		resultadoEnvio="";
		progresoEnvio="";
		envioProfesores="";
		envioPosgrado="";
		envioAdministrativos="";
		envioPregrado="";
		envioOtros="";
		tipoCorreoSede = "1";
		actualizarFormulario();
		totalListaOtros();
	
	}
	
	public String fechaParametro(int dia, int mes, int ano) {
		String fechaReporte = "";
		fechaReporte = String.valueOf(dia) + " de " + descripcionMes[mes - 1]
				+ " del " + String.valueOf(ano);
		return fechaReporte;
	}
	
	public void actualizarFormulario() {
		//seleccionTipoUsuarioEnvio = tipoCorreoPersona;
		listaTipoBoletinAux =new ArrayList();
		listaTipoBoletin = new ArrayList();
		fechaActual = new Date();
		resultadoEnvio="";                          
		envioProfesores="";
		envioPosgrado="";
		envioAdministrativos="";
		envioPregrado="";
		envioOtros="";
				
		listaTipoBoletinAux= servicioGeneral.obtenerListaObjetos("CorreoTipoPersonaBoletin");
		
		for (int i = 0; i < listaTipoBoletinAux.size(); i++) {
			CorreoTipoPersonaBoletin copAux = (CorreoTipoPersonaBoletin) listaTipoBoletinAux.get(i);
			if(copAux.getEnviado().equals("S") && copAux.getId().intValue() == 0){
				envioProfesores="PROFESORES - enviado";
			}
			
			if(copAux.getEnviado().equals("S") && copAux.getId().intValue() == 1){
				envioPosgrado="POSGRADO - enviado";
			}
			
			if(copAux.getEnviado().equals("S") && copAux.getId().intValue() == 2){
				envioAdministrativos="ADMINISTRATIVOS - enviado";
			}
			
			if(copAux.getEnviado().equals("S") && copAux.getId().intValue() == 3){
				envioPregrado="PREGRADO - enviado";
			}
			
			if(copAux.getEnviado().equals("S") && copAux.getId().intValue() == 4){
				envioOtros="OTROS - enviado";
			}
			
			
		
		}
		
		String fechaS="";
		String fechaD="";
		SimpleDateFormat spd = new SimpleDateFormat("dd");
		SimpleDateFormat spm = new SimpleDateFormat("MM");
		SimpleDateFormat spy = new SimpleDateFormat("yyyy");
			
		listaTipoBoletinAux= servicioGeneral.obtenerListaObjetos("CorreoTipoPersonaBoletin");
		for (int i = 0; i < listaTipoBoletinAux.size(); i++) {
			CorreoTipoPersonaBoletin copAux = (CorreoTipoPersonaBoletin) listaTipoBoletinAux.get(i);
			
			fechaS = fechaParametro(Integer.parseInt(spd
					.format(copAux.getFecha())), Integer.parseInt(spm
					.format(copAux.getFecha())), Integer.parseInt(spy
					.format(copAux.getFecha())));
			
			fechaD = fechaParametro(Integer.parseInt(spd
					.format(fechaActual)), Integer.parseInt(spm
							.format(fechaActual)), Integer.parseInt(spy
							.format(fechaActual)));
			if(!fechaS.equals(fechaD) && copAux.getEnviado().equals("S")){
				copAux.setEnviado("N");
			}
			servicioGeneral.guardarObjeto(copAux);
		
		}
		
		listaTipoBoletin = servicioGeneral.obtenerListaObjetos("CorreoTipoPersonaBoletin");
		tipoCorreoPersonaItem = new SelectItem[listaTipoBoletin.size()];
		for (int i = 0; i < listaTipoBoletin.size(); i++) {
			CorreoTipoPersonaBoletin cop = (CorreoTipoPersonaBoletin)listaTipoBoletin.get(i);
			tipoCorreoPersonaItem[i] = new SelectItem(cop.getId().toString(), cop.getNombre());
		}
		
		tipoCorreoSedeItem = new SelectItem[9];
		tipoCorreoSedeItem[0] = new SelectItem("1", "NACIONAL");
		tipoCorreoSedeItem[1] = new SelectItem("2", "BOGOTÁ");
		tipoCorreoSedeItem[2] = new SelectItem("3", "MEDELLÍN");
		tipoCorreoSedeItem[3] = new SelectItem("4", "MANIZALES");
		tipoCorreoSedeItem[4] = new SelectItem("5", "PALMIRA");
		tipoCorreoSedeItem[5] = new SelectItem("6", "AMAZONIA");
		tipoCorreoSedeItem[6] = new SelectItem("7", "ORINOQUIA");
		tipoCorreoSedeItem[7] = new SelectItem("8", "CARIBE");
		tipoCorreoSedeItem[8] = new SelectItem("9", "TUMACO");
	}

	public void seleccionarTipoEnvio() {
		seleccionTipoUsuarioEnvio = tipoCorreoPersona;
	}

	public void cargarCorreoInicial() {

		// correoPersonaBoletin = new CorreoPersonaBoletin();
		// correoPersonaBoletin =(CorreoPersonaBoletin)
		// servicioGeneral.obtenerCorreoBoletin(1);
		//resultadoEnvio="";
		progresoEnvio="";
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(59);// 42
		asuntoBoletin = correoActual.getAsunto();
		cuerpoBoletin = correoActual.getCuerpo();

	}

	public void cargarCorreoBoletin() throws SQLException {
		resultadoEnvio="";
		progresoEnvio="";
		System.out.print("Carga de datos, se debe validar en este momento");
		if ((cuerpoBoletin != null && asuntoBoletin != null)
				|| (!cuerpoBoletin.equals("") && !asuntoBoletin.equals(""))) {
			CorreoPlantilla correoPlantilla = new CorreoPlantilla();
			correoPlantilla.setId(new Long("59"));
			correoPlantilla.setCuerpo(cuerpoBoletin);
			correoPlantilla.setAsunto(asuntoBoletin);
			correoPlantilla.setNombre("boletinDIB");
			servicioGeneral.guardarObjeto(correoPlantilla);

			// Actualiza la plantilla en PRUEBAS
			servicioGeneral
					.eliminar("UPDATE her_correo_plantilla@bdpp02 SET COP_CUERPO =(SELECT COP_CUERPO FROM HER_CORREO_PLANTILLA WHERE COP_ID=59), COP_ASUNTO = (SELECT COP_ASUNTO FROM HER_CORREO_PLANTILLA WHERE COP_ID=59) WHERE COP_ID=59");

			resultadoEnvio = "Datos almacenados correctamente.";
			resultadoEnvio();
			
			// resultadoEnvio
			// ="Los datos no se pueden almacenar, debe agregar el asunto y el cuerpo del mensaje.";
		}
		cargarCorreoInicial();
	}

	void resultadoEnvio() {
		FacesContext.getCurrentInstance().addMessage(
				"",
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						resultadoEnvio, null));
	}
	
	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux=(CorreoPlantilla)
		// CorreoPlantilla a = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux3 = (CorreoPlantilla) servicioGeneral
		// .obtenerObjeto(a, Long.valueOf(String.valueOf(cod_id)));
		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		// String
		// correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
		return correoActualAux;
	}

	public void enviarBoletinPrueba() {
		resultadoEnvio="";
		progresoEnvio="";
		CorreoPlantilla correoActual = new CorreoPlantilla();

		correoActual = cargarPlantilla(59);// 42
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_BOLETIN);

		String dirCorreo = dirPrueba;
		correo.adicionarDireccion(dirCorreo);
		//correo.adicionarCopiaOculta("wamartinezma@gmail.com");
		correo.setAsunto("PRUEBA BOLETÍN - " + correoActual.getAsunto());
		correo.setCuerpo(correoActual.getCuerpo());
		
		try {
			servicioCorreo.enviarCorreoBoletin(correo);
			personaActual = (Persona) sesion.getAttribute("persona");
			
			CorreoBoletinDetalle correoBoletinDetalle = new CorreoBoletinDetalle();
			
			String fechaP="";
			SimpleDateFormat spd = new SimpleDateFormat("dd");
			SimpleDateFormat spm = new SimpleDateFormat("MM");
			SimpleDateFormat spy = new SimpleDateFormat("yyyy");
			
			

			fechaP = fechaParametro(Integer.parseInt(spd
					.format(fechaActual)), Integer.parseInt(spm
					.format(fechaActual)), Integer.parseInt(spy
					.format(fechaActual)));
			
			
			
			correoBoletinDetalle.setFecha(fechaP);
			correoBoletinDetalle.setTipo(1);
			correoBoletinDetalle.setTipoDocumento(personaActual.getId().getTipoDocumento());
			correoBoletinDetalle.setTipoPersonaCorreo(0);
			correoBoletinDetalle.setDocumentoPersona(personaActual.getId().getDocumento());
			
			servicioGeneral.guardarObjeto(correoBoletinDetalle);
			resultadoEnvio = "Correo de prueba enviado correctamente.";
			resultadoEnvio();
		} catch (Exception mex) {
			resultadoEnvio = "Correo de prueba no enviado correctamente.";
			resultadoEnvio();
		}
	}

	public String enviarCorreo() throws DataAccessException, SQLException {

		int numeroRegistros = 1;
		int totalCortes = 0;
		int residuoCorte = 0;
		int valorUltimoRegistro = 0;
		int contadora = 5;
		int inicio = 0;
		String mensajeConfirmacion="";
		List listaBoletinDetalle;
		String fechaB="";
		String fechaE="";
		String fechaV ="";
		String direccionInicio ="";
		String numeroInicio="";
		
		List correoBoletinDIB;
		List listaTipoCorreoBoletin;
		resultadoEnvio="";
		progresoEnvio="";
		listaTipoCorreoBoletin = new ArrayList();
		listaBoletinDetalle = new ArrayList();
		
		CorreoTipoPersonaBoletin correoTipoPersonaBoletin = new CorreoTipoPersonaBoletin();
		personaActual = (Persona) sesion.getAttribute("persona");
		// JProgressBar mibar;
		SimpleDateFormat spd = new SimpleDateFormat("dd");
		SimpleDateFormat spm = new SimpleDateFormat("MM");
		SimpleDateFormat spy = new SimpleDateFormat("yyyy");
		
		

		fechaV = fechaParametro(Integer.parseInt(spd
				.format(fechaActual)), Integer.parseInt(spm
				.format(fechaActual)), Integer.parseInt(spy
				.format(fechaActual)));

		
		listaBoletinDetalle = servicioGeneral.obtenerListaObjetos("CorreoBoletinDetalle WHERE tipo = 1 AND documentoPersona = '"+ personaActual.getId().getDocumento()+"' AND tipoDocumento = '"+ personaActual.getId().getTipoDocumento()+"' AND fecha = '"+ fechaV+"'" );
			
		if (listaBoletinDetalle != null && listaBoletinDetalle.size()>0){
		
		
			if (!nombreBoton.equals("Enviando......................")) {
	
				nombreBoton = "Enviando......................";
				correoBoletinDIB = new ArrayList();
				CorreoPlantilla correoActual = new CorreoPlantilla();
				correoActual = cargarPlantilla(59);// 42
				CorreoPersonaBoletin correoPersonaBoletinAux = new CorreoPersonaBoletin();
				
				
				Persona persona = new Persona();
				persona = (Persona) sesion.getAttribute("persona");
				
				Dependencia dependencia;
				
				dependencia = new Dependencia();
				try {
						persona = servicioPersona
								.obtenerInvestigadorInternoCompleto(persona
										.getId());
						InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
						dependencia = servicioDependencia
								.obtenerDependencia(investigadorInterno.getId());

						
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				if(tipoCorreoSede.equals("1")){
					correoBoletinDIB = new ArrayList();
					
					CorreoPersonaBoletin correoPersonaBoletin = new CorreoPersonaBoletin();
					
					if(tipoCorreoPersona.equals("0")){
						//Profesores
						correoPersonaBoletin.setId(new Long(19380666));
						correoPersonaBoletin.setCorreoPersona("docentes@unal.edu.co");
						correoBoletinDIB.add(correoPersonaBoletin);
					}else if(tipoCorreoPersona.equals("1") || tipoCorreoPersona.equals("3")){
						//Estudiantes
						correoPersonaBoletin.setId(new Long(19380666));
						correoPersonaBoletin.setCorreoPersona("estudiantes@unal.edu.co");
						correoBoletinDIB.add(correoPersonaBoletin);
					}else if(tipoCorreoPersona.equals("2")){
						//Administrativos
						correoPersonaBoletin.setId(new Long(19380666));
						correoPersonaBoletin.setCorreoPersona("administrativos@unal.edu.co");
						correoBoletinDIB.add(correoPersonaBoletin);
					}else if(tipoCorreoPersona.equals("4")){
						correoBoletinDIB = servicioGeneral.obtenerCorreoBoletin(Integer
								.parseInt(tipoCorreoPersona),tipoCorreoSede);						
					}
					
					
				}else{			
					correoBoletinDIB = servicioGeneral.obtenerCorreoBoletin(Integer
							.parseInt(tipoCorreoPersona),tipoCorreoSede);
				}
				//JProgressBar mibar; // definimos la barra de progreso
				//mibar = new JProgressBar(0, correoBoletinDIB.size());
				//mibar.setValue(0);
				//mibar.setStringPainted(true); // las marcas se deben mostrar .....
	
				if (correoBoletinDIB != null && correoBoletinDIB.size() > 0) {
					int contador = 0;
					for (int i = 0; i < correoBoletinDIB.size(); i++) {
						contador++;
						correoPersonaBoletinAux = (CorreoPersonaBoletin) correoBoletinDIB
								.get(i);
						Correo correo = new Correo();
						correo.setOrigen(Correo.CORREO_BOLETIN);
						String dirCorreo = correoPersonaBoletinAux
								.getCorreoPersona();
						boolean bandera = true;
						
						if(dirCorreo==null || dirCorreo.length() <= 0){
							bandera = false;
						}
						
						if(bandera){
							correo.adicionarDireccion(dirCorreo.trim());
													
							// correo.adicionarCopiaOculta("wamartinezma@gmail.com");
							correo.setAsunto(correoActual.getAsunto());
							correo.setCuerpo(correoActual.getCuerpo());
						//	mibar.setValue(i);
						//	mibar.setStringPainted(true); // las marcas se deben mostrar
							
							if (dirCorreo !=null && !dirCorreo.equals("")){
								progresoEnvio = i+1 + dirCorreo;
								System.out.print("progreso............." + progresoEnvio);
								if (i==0){
									direccionInicio = dirCorreo;
									numeroInicio= String.valueOf(i);
								}
								
								SimpleDateFormat spd1 = new SimpleDateFormat("dd");
								SimpleDateFormat spm1 = new SimpleDateFormat("MM");
								SimpleDateFormat spy1 = new SimpleDateFormat("yyyy");
								
								fechaE = fechaParametro(Integer.parseInt(spd1
										.format(fechaActual)), Integer.parseInt(spm1
										.format(fechaActual)), Integer.parseInt(spy1
										.format(fechaActual)));
						
							}
							
						
							
							// .....
							// Task task;
							// task = new Task();
							// task.addPropertyChangeListener(this);
							// task.execute();
							// mibar.setValue(i);
						
						//	mibar.setVisible(true);
							if (i == correoBoletinDIB.size() - 1) {
								nombreBoton = "Enviar Boletín";
								listaTipoCorreoBoletin = servicioGeneral.obtenerListaObjetos("CorreoTipoPersonaBoletin where id ='"+Integer.parseInt(tipoCorreoPersona)+"'");
								correoTipoPersonaBoletin = (CorreoTipoPersonaBoletin)listaTipoCorreoBoletin.get(0);
								correoTipoPersonaBoletin.setEnviado("S");
								correoTipoPersonaBoletin.setFecha(fechaActual);
								servicioGeneral.guardarObjeto(correoTipoPersonaBoletin);
															
								SimpleDateFormat spd1 = new SimpleDateFormat("dd");
								SimpleDateFormat spm1 = new SimpleDateFormat("MM");
								SimpleDateFormat spy1 = new SimpleDateFormat("yyyy");
								
								fechaE = fechaParametro(Integer.parseInt(spd1
										.format(fechaActual)), Integer.parseInt(spm1
										.format(fechaActual)), Integer.parseInt(spy1
										.format(fechaActual)));
								
								CorreoBoletinDetalle correoBoletinDetalle = new CorreoBoletinDetalle();
								correoBoletinDetalle.setFecha(fechaE);
								correoBoletinDetalle.setTipo(2);
								correoBoletinDetalle.setTipoDocumento(personaActual.getId().getTipoDocumento());
								correoBoletinDetalle.setTipoPersonaCorreo(Integer.parseInt(tipoCorreoPersona));
								correoBoletinDetalle.setDocumentoPersona(personaActual.getId().getDocumento());
								//correoBoletinDetalle.setUltimaDireccion(ultimaDireccion);
								//correoBoletinDetalle.setIdUltimaDireccion(idUltimaDireccion);
								
								servicioGeneral.guardarObjeto(correoBoletinDetalle);
								List listaParametro;
								listaParametro = new ArrayList();
								
								/*Persona personaAux = new Persona();
								listaParametro = this.servicioGeneral
								.obtenerObjetos("FROM Parametro WHERE nombre = 'DIRECTOR_DIB'");
	
								if (listaParametro != null && listaParametro.size() > 0) {
									Parametro par = (Parametro) listaParametro.get(0);
									
									IdPersona id = new IdPersona();
								    id.setDocumento(par.getValor());
								    id.setTipoDocumento("C");
								    personaAux=servicioPersona.obtenerPersona(id);
								}*/
								
								
								personaActual = (Persona) sesion.getAttribute("persona");
								int totalPersonas =0;
								totalPersonas = correoBoletinDIB.size() + 1 ;
								mensajeConfirmacion = "El Boletín se ha enviado exitosamente a " + String.valueOf(totalPersonas) + " Personas - GRUPO - "+correoTipoPersonaBoletin.getNombre()+ "\n"
								+ "Dirección de correo electrónica de inicio: " + numeroInicio + " - " + direccionInicio +  "\n"
								+ "Dirección de correo electrónica de finalización: " + totalPersonas + " - " + dirCorreo +  "\n";
								
								Correo correoConfirmacion = new Correo();
								correo.setOrigen(correoConfirmacion.CORREO_BOLETIN);
								String dirCorreoConfirmacion = personaActual.getEmail();
								correoConfirmacion.adicionarDireccion(dirCorreoConfirmacion);
								//correoConfirmacion.adicionarCopiaOculta("sisii_nal@unal.edu.co");
								
						    	correoConfirmacion.setAsunto("Informe de envío exitoso del Boletín UN Investiga - " + fechaE + " - grupo - "  + correoTipoPersonaBoletin.getNombre()  );
								correoConfirmacion.setCuerpo(mensajeConfirmacion);
								servicioCorreo.enviarCorreoBoletin(correoConfirmacion);						
								actualizarFormulario();
							}
							try {
								if(contador == 80){
									Thread.sleep(45000);
									contador =0;
								}
								
								servicioCorreo.enviarCorreoBoletin(correo);
								resultadoEnvio = "Boletín envíado correctamente.";
								resultadoEnvio();
							}catch (Exception mex) {
								
							}
						}
					}
				}
			}else{
				resultadoEnvio = "El boletín se está enviando......................";
				resultadoEnvio();
			}
		}else{
			resultadoEnvio = "El boletín no se puede enviar hasta que genere una prueba de envío para la fecha actual.";
			resultadoEnvio();
		}
		return "";
	}


	private void limpiarCampos() {
		correo.setAsunto("");
		correo.setCuerpo("");
		cedula = "";
	}

	/**
	 * Funcion que crea la ruta completa donde se va a guardar el archivo
	 * 
	 * @return la ruta del archivo o null si no lo pudo crear
	 */
	private String crearRutaCompletaArchivo(String nombre) {

		ServletContext context = (ServletContext) facesContext
				.getExternalContext().getContext();
		String rutaArchivo = context.getRealPath(RUTA_ADJUNTO);
		rutaArchivo += "\\" + nombre;

		return rutaArchivo;
	}

	/**
	 * Aplica al correo la plantilla dada en el parametro plantilla
	 * 
	 * @param correo
	 * @param plantilla
	 */
	public void aplicarPlantillaCorreo(Correo correo, CorreoPlantilla plantilla) {
		String rutaAdjunto = crearRutaCompletaArchivo(plantilla
				.getNombreAdjunto());
		servicioCorreo.aplicarPlantillaCorreo(correo, plantilla, rutaAdjunto);
	}
	
	public void totalListaOtros(){
		
		try{
			
			totalListaOtros = servicioGeneral.obtenerTotalListaOtrosBoletin();
			if (totalListaOtros > 0L){
				totalSuscritos = totalListaOtros - 323;
			}
		
		}catch (Exception e){
			
		}
		
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getResultadoEnvio() {
		return resultadoEnvio;
	}

	public void setResultadoEnvio(String resultadoEnvio) {
		this.resultadoEnvio = resultadoEnvio;
	}

	public String getAsuntoBoletin() {
		return asuntoBoletin;
	}

	public void setAsuntoBoletin(String asuntoBoletin) {
		this.asuntoBoletin = asuntoBoletin;
	}

	public String getCuerpoBoletin() {
		return cuerpoBoletin;
	}

	public void setCuerpoBoletin(String cuerpoBoletin) {
		this.cuerpoBoletin = cuerpoBoletin;
	}

	public String getDirPrueba() {
		return dirPrueba;
	}

	public void setDirPrueba(String dirPrueba) {
		this.dirPrueba = dirPrueba;
	}

	public CorreoPersonaBoletin getCorreoPersonaBoletin() {
		return correoPersonaBoletin;
	}

	public void setCorreoPersonaBoletin(
			CorreoPersonaBoletin correoPersonaBoletin) {
		this.correoPersonaBoletin = correoPersonaBoletin;
	}

	public String getTipoCorreoPersona() {
		return tipoCorreoPersona;
	}

	public void setTipoCorreoPersona(String tipoCorreoPersona) {
		this.tipoCorreoPersona = tipoCorreoPersona;
	}

	public SelectItem[] getTipoCorreoPersonaItem() {
		return tipoCorreoPersonaItem;
	}

	public void setTipoCorreoPersonaItem(SelectItem[] tipoCorreoPersonaItem) {
		this.tipoCorreoPersonaItem = tipoCorreoPersonaItem;
	}

	public String getSeleccionTipoUsuarioEnvio() {
		return seleccionTipoUsuarioEnvio;
	}

	public void setSeleccionTipoUsuarioEnvio(String seleccionTipoUsuarioEnvio) {
		this.seleccionTipoUsuarioEnvio = seleccionTipoUsuarioEnvio;
	}

	public String getNombreBoton() {
		return nombreBoton;
	}

	public void setNombreBoton(String nombreBoton) {
		this.nombreBoton = nombreBoton;
	}

	public String getProgresoEnvio() {
		return progresoEnvio;
	}

	public void setProgresoEnvio(String progresoEnvio) {
		this.progresoEnvio = progresoEnvio;
	}

	public List getListaTipoBoletin() {
		return listaTipoBoletin;
	}

	public void setListaTipoBoletin(List listaTipoBoletin) {
		this.listaTipoBoletin = listaTipoBoletin;
	}

	public List getListaTipoBoletinAux() {
		return listaTipoBoletinAux;
	}

	public void setListaTipoBoletinAux(List listaTipoBoletinAux) {
		this.listaTipoBoletinAux = listaTipoBoletinAux;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getEnvioProfesores() {
		return envioProfesores;
	}

	public void setEnvioProfesores(String envioProfesores) {
		this.envioProfesores = envioProfesores;
	}

	public String getEnvioPosgrado() {
		return envioPosgrado;
	}

	public void setEnvioPosgrado(String envioPosgrado) {
		this.envioPosgrado = envioPosgrado;
	}

	public String getEnvioAdministrativos() {
		return envioAdministrativos;
	}

	public void setEnvioAdministrativos(String envioAdministrativos) {
		this.envioAdministrativos = envioAdministrativos;
	}

	public String getEnvioPregrado() {
		return envioPregrado;
	}

	public void setEnvioPregrado(String envioPregrado) {
		this.envioPregrado = envioPregrado;
	}

	public String getEnvioOtros() {
		return envioOtros;
	}

	public void setEnvioOtros(String envioOtros) {
		this.envioOtros = envioOtros;
	}

	public String[] getDescripcionMes() {
		return descripcionMes;
	}

	public void setDescripcionMes(String[] descripcionMes) {
		this.descripcionMes = descripcionMes;
	}

	public SelectItem[] getTipoCorreoSedeItem() {
		return tipoCorreoSedeItem;
	}

	public void setTipoCorreoSedeItem(SelectItem[] tipoCorreoSedeItem) {
		this.tipoCorreoSedeItem = tipoCorreoSedeItem;
	}

	public String getTipoCorreoSede() {
		return tipoCorreoSede;
	}

	public void setTipoCorreoSede(String tipoCorreoSede) {
		this.tipoCorreoSede = tipoCorreoSede;
	}

	public Long getTotalListaOtros() {
		return totalListaOtros;
	}

	public void setTotalListaOtros(Long totalListaOtros) {
		this.totalListaOtros = totalListaOtros;
	}

	public Long getTotalSuscritos() {
		return totalSuscritos;
	}

	public void setTotalSuscritos(Long totalSuscritos) {
		this.totalSuscritos = totalSuscritos;
	}

}
