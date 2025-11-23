package co.edu.unal.hermes.vista.aval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;

public class ManejadorSolicitarAvalCESI extends BaseManejadorSolicitarAvalDireccion {

    private static final long serialVersionUID = 1L;
    private Boolean esQueja = false;

    public ManejadorSolicitarAvalCESI() {
        InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
        List<Aval> listaAvalAux = servicioGeneral.obtenerAvalesCESI(invInterno.getDependencia2().getId());
        List<Aval> listaAvalAuxQueja = servicioGeneral.obtenerAvalesCESIQueja(invInterno.getDependencia2().getId());
        
        listaAvalQueja = new ArrayList<Aval>();
        
        organizarListaRevisionAval(listaAvalAux);
        organizarListaRevisionAvalQueja(listaAvalAuxQueja);
    }

    public String editarAval() {
        consultarAvalTramitar();
        esQueja = false;
//        listaplantilla = servicioAval.obtenerListaCartas(aval.getTipo());
//        if (!esListaVacia(listaplantilla)) {
//            plantillas = new SelectItem[listaplantilla.size()];
//            int i = 0;
//            for (Iterator<Reporte> ic = listaplantilla.iterator(); ic.hasNext();) {
//                Reporte p = ic.next();
//                plantillas[i] = new SelectItem(p.getId().toString(), p.getNombreExterno());
//                i++;
//            }
//        }
        return "consultaAvalGenerarCESI";
    }
    
    public String editarAvalQueja() {
        consultarAvalTramitar();
        esQueja = true;
//        listaplantilla = servicioAval.obtenerListaCartas(aval.getTipo());
//        if (!esListaVacia(listaplantilla)) {
//            plantillas = new SelectItem[listaplantilla.size()];
//            int i = 0;
//            for (Iterator<Reporte> ic = listaplantilla.iterator(); ic.hasNext();) {
//                Reporte p = ic.next();
//                plantillas[i] = new SelectItem(p.getId().toString(), p.getNombreExterno());
//                i++;
//            }
//        }
        return "consultaAvalGenerarCESI";
    }
    
    public void organizarListaRevisionAvalQueja(List<Aval> listaAvalAux){
    		
        String valoresParam = "";
        List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
                "WHERE p.nombre = 'T_AVAL' AND p.valor= '" + personaActual.getId().getDocumento() + "'");
        if (!esListaVacia(listaAvalAux)) {
            for (int j = 0; j < listaAvalAux.size(); j++) {
                Aval avalAux = listaAvalAux.get(j);

                if (!esListaVacia(listaParametro)) {
                    Parametro paActual = listaParametro.get(0);
                    valoresParam = paActual.getDescripcion();

                    if (!esCadenaVacia(valoresParam)) {
                        StringTokenizer tokens = new StringTokenizer(valoresParam);
                        while (tokens.hasMoreTokens()) {
                            if (avalAux.getTipo().equals(tokens.nextToken().trim())) {
                                Persona persona2 = servicioPersona.obtenerPersona(
                                        new IdPersona(avalAux.getDocumento(), avalAux.getTipoDocumento()));
                                avalAux.setNombreInvestigador(persona2.getNombreCompletoMinusculas());
                                listaAvalQueja.add(avalAux);
                            }
                        }
                    }
                } else {
                    Persona persona2 = servicioPersona
                            .obtenerPersona(new IdPersona(avalAux.getDocumento(), avalAux.getTipoDocumento()));
                    avalAux.setNombreInvestigador(persona2.getNombreCompletoMinusculas());
                    listaAvalQueja.add(avalAux);
                }
            }
        }
    }

//    public void reporteAval() throws SQLException {
//        if (esCadenaVacia(avalId)) {
//            mensajeError("Debe seleccionar una plantilla de carta para generarla.");
//            return;
//        }
//
//        ReporteBirt r = new ReporteBirt();
//        personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
//        InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
//
//        servicioGeneral.ejecutarSentencia("UPDATE HER_AVAL SET AVI_TEXTO_COMP_COOR = '" + aval.getAviTextoCompCoor()
//                + "' WHERE AVI_ID = '" + aval.getAviId().toString() + "'");
//        String duracionMeses = "---";
//        String valorContrapartidaLetras = "";
//        if (aval.isEsRegalias()) {
//            valorContrapartidaLetras = convertir(
//                    String.valueOf(aval.getAviEspecieunal() + aval.getValorPersonalTotal()), false);
//
//            Proyecto proyecto = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
//                    ProyectoDAOHibernate.DATOS_BASICOS);
//            duracionMeses = convertirDuracionMeses(proyecto.getDuracionTipo(), proyecto.getDuracion());
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        r.adicionarParametro("valorletras", valorContrapartidaLetras);
//        r.adicionarParametro("mes", getNombreMes(calendar.get(Calendar.MONTH)));
//        r.adicionarParametro("valorNumero", String.valueOf(aval.getAviEspecieunal() + (aval.getValorPersonalTotal())));
//        r.adicionarParametro("Id", aval.getAviId().toString());
//        r.adicionarParametro("Decano", "NO");
//        r.adicionarParametro("Ciu", "Bogotá D.C.");
//        r.adicionarParametro("Sed", invInterno.getDependencia().getSede().getNombre());
//        r.adicionarParametro("meses", duracionMeses);
//        r.adicionarParametro("d", "1");
//        r.setNombreReporte(obtenerNombreCarta(avalId));
//        r.setFormato(ReporteBirt.FORMATO_PDF);
//        sesion.setAttribute("reporte", r);
//        FacesContext context = FacesContext.getCurrentInstance();
//        r.run(context);
//    }

    public String guardarRevision() {
        if (aval == null) {
            return "";
        }
        personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
        boolean temp = false;
        aval.setAvalCESI("");
        
        if (esNulo(selItem) || selItem == 0) {
			mensajeError("Debe seleccionar si aprueba o no aprueba el aval");
			return "";
		}
        
        if (this.selItem == 2) {
			this.aval.setAviEstado(Aval.ETICO_APROBADO_CESI);
			aval.setAvalCESI(Aval.APROBADO);
			temp = true;
			if (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0) {
				mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
				return "";
			}
		} else if (this.selItem == 3) {
			if (esCadenaVacia(aval.getTextoCESI())) {
				mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval");
				return "";
			}
			this.aval.setAviEstado(Aval.ETICO_NO_APROBADO_CESI);
			aval.setAvalCESI(Aval.NEGADO);
			temp = true;
		}

        if (temp) {
            Persona personaAux = servicioPersona.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
            aval.setFechaAvalCESI(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);
			
			if (!esNulo(aval.getAvalCESI()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CESI)) {
				correoActual = cargarPlantilla(380);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			} else if (!esNulo(aval.getAvalCESI()) && aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CESI)) {
				correoActual = cargarPlantilla(381);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			}
			
            crearHistoricoEstadoAval(aval, personaActual, "CESI");
            listaAval = servicioGeneral.obtenerAvalesCESI(aval.getCepi().getCesi().getDependencia().getId());
            mensajeInfo("Guardado con éxito");
            sesion.removeAttribute("manejadorSolicitarAvalCESI");
        }
        return "avalarCESI";
    }
    
    public String guardarRevisionQueja() {
        if (aval == null) {
            return "";
        }
        personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
        boolean temp = false;
        
		if (esCadenaVacia(aval.getTextoCESIQueja())) {
			mensajeError("Debe ingresar los comentarios u observaciones sobre la revisión de la queja");
			return "";
		}

		temp = true;

        if (temp) {
            Persona personaAux = servicioPersona.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
            aval.setAvalCESIQueja("S");
            aval.setFechaCESIQueja(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);
			
			correoActual = cargarPlantilla(383);
			Correo correo = editarCorreoQueja(personaAux, aval);
			servicioCorreo.enviarCorreo(correo);
			
            crearHistoricoEstadoAval(aval, personaActual, "CESI-Queja");
            listaAvalQueja = servicioGeneral.obtenerAvalesCESIQueja(aval.getCepi().getCesi().getDependencia().getId());
            mensajeInfo("Guardado con éxito");
            sesion.removeAttribute("manejadorSolicitarAvalCESI");
        }
        return "avalarCESI";
    }
    
//    public String guardarRevisionRecurso() {
//        if (aval == null) {
//            return "";
//        }
//        personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
//        boolean temp = false;
//        aval.setAvalCEPIRecurso("");
//        
//        if(aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposicion) 
//        		|| aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposición_Apelación)) {
//        	
//        	if (esNulo(selItem) || selItem == 0) {
//				mensajeError("Debe seleccionar si aprueba o no el aval luego de la revisión del recurso");
//				return "";
//			}
//        	
//	        if (this.selItem == 2) {
//				this.aval.setAviEstado(Aval.ETICO_APROBADO_CEPI);
//				aval.setAvalCEPIRecurso(Aval.APROBADO);
//				temp = true;
//				if (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0) {
//					mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
//					return "";
//				}
//			} else if (this.selItem == 3) {
//				if (esCadenaVacia(aval.getTextoCEPI())) {
//					mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval luego de la revisión del recurso");
//					return "";
//				}
//				this.aval.setAviEstado(Aval.ETICO_NO_APROBADO_CEPI);
//				aval.setAvalCEPIRecurso(Aval.NEGADO);
//				temp = true;
//			}
//        } else if(aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Apelacion)) {
//        	
//        	if (esNulo(selItem) || selItem == 0) {
//				mensajeError("Debe seleccionar si el recurso de apelación es procedente o no");
//				return "";
//			}
//        	
//        	if (this.selItem == 2) {
//        		aval.setAvalCEPIRecurso(Aval.APROBADO);
//        		temp = true;
//			} else if (this.selItem == 3) {
//				aval.setAvalCEPIRecurso(Aval.NEGADO);
//				temp = true;
//			}
//        }
//
//        if (temp) {
//            Persona personaAux = servicioPersona.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
//            aval.setFechaAvalCEPIRecurso(new Date());
//			this.servicioGeneral.guardarObjeto(this.aval);
//			
//			int tipoRecursoId = !esNulo(aval.getTipoRecursoAvalEtico()) ?  aval.getTipoRecursoAvalEtico().getId().intValue() : 0;
//			
//			switch(tipoRecursoId) {
//				// Reposición
//				case 7685:
//					if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI)) {
//						correoActual = cargarPlantilla(372);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//					} else if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CEPI)) {
//						correoActual = cargarPlantilla(373);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//					}
//				break;
//				// Reposición con apelación
//				case 7686:
//					if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI)) {
//						correoActual = cargarPlantilla(374);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//					} else if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CEPI)) {
//						correoActual = cargarPlantilla(375);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//						//Falta 376
//						correoActual = cargarPlantilla(376);
//						Correo correoCESI = editarCorreoCESI(personaAux, aval);
//						servicioCorreo.enviarCorreo(correoCESI);
//					}
//				break;
//				// Apelación
//				case 7687:
//					if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAvalCEPIRecurso().equals(Aval.APROBADO)) {
//						correoActual = cargarPlantilla(377);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//						//Falta 378
//						correoActual = cargarPlantilla(378);
//						Correo correoCESI = editarCorreoCESI(personaAux, aval);
//						servicioCorreo.enviarCorreo(correoCESI);
//					} else if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAvalCEPIRecurso().equals(Aval.NEGADO)) {
//						correoActual = cargarPlantilla(379);
//						Correo correo = editarCorreo(personaAux, aval);
//						servicioCorreo.enviarCorreo(correo);
//						
//					}
//				break;
//				//Default
//				default:
//				break;
//			}
//            crearHistoricoEstadoAval(aval, personaActual, "CEPI-Recurso");
//            listaAval = servicioGeneral.obtenerAvalesCEPI(aval.getCepi().getDependencia().getId());
//            mensajeInfo("Guardado con éxito");
//            sesion.removeAttribute("manejadorSolicitarAvalCEPI");
//        }
//        return "avalarCEPI";
//    }

    public Correo editarCorreo(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", aval.getTextoCESI()));
			correo = correo.replaceAll("<<NOMBRE_DEPENDENCIA>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getCesi().getDependencia().getNombre() : "ERROR DEPENDENCIA CESI");
			correo = correo.replaceAll("<<NOMBRE_CEPI>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getNombre() : "ERROR NOMBRE CEPI");
			
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			//correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoElectronico.adicionarDireccion(personaAux.getEmail());
						
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			
			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
    }
    
    public Correo editarCorreoQueja(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", aval.getTextoCESIQueja()));
			correo = correo.replaceAll("<<NOMBRE_DEPENDENCIA>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getCesi().getDependencia().getNombre() : "ERROR DEPENDENCIA CESI");
			correo = correo.replaceAll("<<NOMBRE_CEPI>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getNombre() : "ERROR NOMBRE CEPI");
			
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			//correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoElectronico.adicionarDireccion(personaAux.getEmail());
						
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			
			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
    }
    
    public Correo editarCorreoCESI(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", nAval.getTextoCEPI()));
			correo = correo.replaceAll("<<NOMBRE_DEPENDENCIA>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getCesi().getDependencia().getNombre() : "ERROR DEPENDENCIA CESI");
			correo = correo.replaceAll("<<NOMBRE_CEPI>>", !esNulo(nAval.getCepi()) ? nAval.getCepi().getNombre() : "ERROR NOMBRE CEPI");
			
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			//correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			
			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
					" JOIN i.roles r WHERE r.id = 'CESI' AND i.dependencia2.id = '"+ nAval.getCepi().getCesi().getDependencia().getId().toString() + "'")) {
				if (!esNulo(inv.getEmail())) 
					correoElectronico.adicionarDireccion(inv.getEmail());
			}
			
//			correoElectronico.adicionarDireccion(personaAux.getEmail());
						
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			
			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
    }

    public String anterior() {
        sesion.removeAttribute("manejadorSolicitarAvalCESI");
        return "avalarCESI";
    }

    public int getTamañoLista() {
        if (listaAval != null) {
            return listaAval.size();
        }
        return 0;
    }
    
    public int getTamañoListaQueja() {
        if (listaAvalQueja != null) {
            return listaAvalQueja.size();
        }
        return 0;
    }

	public Boolean getEsQueja() {
		return esQueja;
	}

	public void setEsQueja(Boolean esQueja) {
		this.esQueja = esQueja;
	}

}
