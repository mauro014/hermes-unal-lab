package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.GruposCursosECP;
import co.edu.unal.hermes.modelo.GruposHorariosECP;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.OfertaHorariosECP;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;

public class ManejadorEditarCursoECP extends ManejadorBase {

	private Proyecto curso;
	private List listaObjetivos;
	private String modalidadEcp;
	private String claseEvento;
	private String claseEventoExt;
	//public List horario = new ArrayList<String>();
	public String horario;
	public Date horaInicio;
	public Date horafinal;

	private String sedeSel;
	private String facultadSel;
	private List<Dependencia> facultadesUN;
	public SelectItem[] facultadItem;
	private List<Sede> sedesUN;
	public SelectItem[] sedeItem;
	public List<SelectItem> listaEstadosProyectoItem;
	private List<SelectItem> listaGruposItems = new ArrayList<SelectItem>();
	private List<SelectItem> listaDiasItems = new ArrayList<SelectItem>();
	private int grupoSel;
	private int numGrupoInscritos;
	private String descripcionGrupo;
	private List<GruposCursosECP> listaGruposCurso;
	private GruposCursosECP grupoBorrarSeleccionado;
	private int grupoHorario;
	private List<SelectItem> listaGruposHorarioItems = new ArrayList<SelectItem>();
	private List<GruposHorariosECP> listaGrupoHorarioDia;
	private GruposHorariosECP grupoHorarioBorrarSeleccionado;
	

	public ManejadorEditarCursoECP() {
		// sesion.removeAttribute("ManejadorCursosAvaladosECP");
		curso = (Proyecto) sesion.getAttribute("cursoECP");
		listaObjetivos = new ArrayList<ObjetivoEspecifico>();
		listaEstadosProyectoItem = new ArrayList<SelectItem>();

		listaGruposItems = new ArrayList<SelectItem>();
		listaGruposItems.add(new SelectItem(1));
		numGrupoInscritos = 1;
		listaGruposCurso = new ArrayList<GruposCursosECP>();

		listaDiasItems = new ArrayList<SelectItem>();
		listaDiasItems.add(new SelectItem("Lunes", "Lunes"));
		listaDiasItems.add(new SelectItem("Martes", "Martes"));
		listaDiasItems.add(new SelectItem("Miércoles", "Miércoles"));
		listaDiasItems.add(new SelectItem("Jueves", "Jueves"));
		listaDiasItems.add(new SelectItem("Viernes", "Viernes"));
		listaDiasItems.add(new SelectItem("Sábado", "Sábado"));
		listaDiasItems.add(new SelectItem("Domingo", "Domingo"));
		
		listaGrupoHorarioDia = new ArrayList<GruposHorariosECP>();

		if (curso != null) {
			cargarListaObjetivos();
			cargarObjetosDominios();
			cargarSedesFacultades();
			cargarEstadosCurso();
		} else
			System.out.println("Curso parametro en sesion NULO");

		cargarInfoCurso();

	}

	public void cargarInfoCurso() {
		// Horario
		ArrayList<OfertaHorariosECP> listaHorarios = new ArrayList<OfertaHorariosECP>();
		Set horarios = new HashSet();
		horarios = curso.getHorarioCursoECP();
		listaHorarios.addAll(horarios);

		/*if (listaHorarios.size() != 0) {
			for (int i = 0; i < listaHorarios.size(); i++)
				horario.add(listaHorarios.get(i).getId().getDia());

			String[] hinicio = listaHorarios.get(0).getHoraInicio().split(":");
			String[] hfin = listaHorarios.get(0).getHoraFin().split(":");

			horaInicio = new Date(0, 0, 0, Integer.parseInt(hinicio[0]),
					Integer.parseInt(hinicio[0]), 0);
			horafinal = new Date(0, 0, 0, Integer.parseInt(hfin[0]),
					Integer.parseInt(hfin[0]), 0);

		}*/

		// Sedes
		// sedeSel = curso.getSedeEjecucion().getId().toString();

		// Facultad}
		// facultadSel = curso.getFacultadEjecucion().getId().toString();

	}

	public boolean guardar() {
		boolean bandera;
		if (validaECP()) {
			// Horario
			ArrayList<OfertaHorariosECP> horariosList = new ArrayList<OfertaHorariosECP>();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"HH:mm");
			// String fecha = sdf.format(person.getFechaNacimiento());
			Set horarios = new HashSet();
			horarios = curso.getHorarioCursoECP();
			horarios.clear();

		/*	for (int i = 0; i < horario.size(); i++) {
				// curso+=horario[i]+";";
				OfertaHorariosECP oh = new OfertaHorariosECP();
				IdHorariosECP idHora = new IdHorariosECP();
				idHora.setDia((String) horario.get(i));
				idHora.setProyecto(curso);
				oh.setId(idHora);
				oh.setHoraInicio(sdf.format(horaInicio));
				oh.setHoraFin(sdf.format(horafinal));

				horariosList.add(oh);
			}*/
			horarios.addAll(horariosList);

			try {
				curso.setHorarioCursoECP(horarios);
				servicioGeneral.guardarObjeto(curso);
				bandera = true;

				sesion.removeAttribute("ManejadorCursosAvaladosECP");
			} catch (Exception x) {
				x.printStackTrace();
				bandera = false;
			}

		} else {
			System.out.println("Errores de validacion ECP");
			bandera = false;
		}

		if (bandera) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"La informacion del curso ha sido guardada correctamente");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
		}

		return bandera;
	}

	public boolean validaECP() {

		boolean bandera = true;

		// INF General

		float n1 = Float.parseFloat(curso.getCostoPersona());
		if (n1 <= 0.0) {
			bandera = false;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "El valor del curso debe ser mayor a cero ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
		}

		float ni = Float.parseFloat(curso.getMaxAsistentes());
		if (ni <= 0.0) {
			bandera = false;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "El numero de asistentes debe ser mayor a cero ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
		}

		// INF General

		if (curso.getFechaTentativaInicio() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor dígite una fecha de inicio ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (curso.getFechaFinalizacion() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor dígite una fecha de finalización ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (curso.getFechaFinalizacion()
				.before(curso.getFechaTentativaInicio())
				|| curso.getFechaFinalizacion() == curso
						.getFechaTentativaInicio()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"",
					"La fecha de finalización es menor que la fecha de inicio ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		/*if (horario.size() <= 0) {
			bandera = false;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"",
					"Por favor seleccione los días en los que se va a dictar el curso");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
		}*/

		if (horaInicio == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor ingrese una hora de inicio ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (horafinal == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor ingrese una hora de fin ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (!verificarString(curso.getLugar())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"",
					"Por favor ingrese el lugar donde se ofrecerá el curso ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (!verificarString(curso.getMetodologia())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor ingrese la metodología ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		if (!verificarString(curso.getDescripcion())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Por favor ingrese el contenido del curso ");
			FacesContext.getCurrentInstance().addMessage("msgs", msg);
			bandera = false;
		}

		return bandera;
	}

	public boolean verificarString(String myString) {
		if (myString == null) {
			return false;
		}
		String line = "";
		String[] parts = myString.trim().split("[ ]+");
		for (int i = 0; i < parts.length; i++) {
			if (!(parts[i].equals(".") || parts[i].equals(",")
					|| parts[i].equals("/") || parts[i].equals("-")
					|| parts[i].equals("*") || parts[i].equals(" ")
					|| parts[i].equals("!") || parts[i].equals("|")
					|| parts[i].equals("¿") || parts[i].equals("(")
					|| parts[i].equals(")") || parts[i].equals("?")
					|| parts[i].equals("{") || parts[i].equals("}")
					|| parts[i].equals("[") || parts[i].equals("]")
					|| parts[i].equals("_") || parts[i].equals("+")
					|| parts[i].equals("$") || parts[i].equals("#")
					|| parts[i].equals("=") || parts[i].equals(";")
					|| parts[i].equals(":") || parts[i].equals("&")
					|| parts[i].equals("..") || parts[i].equals("...") || parts[i]
						.equals("%"))) {
				line = line + parts[i] + " ";
			}
		}

		return line.length() > 2;
	}

	public void cargarSedesFacultades() {
		// Sedes
		sedesUN = new ArrayList<Sede>();
		sedesUN = servicioGeneral
				.obtenerObjetos("select e from Sede e where  e.id<>0 ");
		sedeItem = new SelectItem[sedesUN.size()];
		for (int i = 0; i < sedesUN.size(); i++) {
			Sede dd = (Sede) sedesUN.get(i);
			sedeItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}

		// Facultades
		facultadesUN = new ArrayList<Dependencia>();
		facultadesUN = servicioGeneral
				.obtenerObjetos("select e from Dependencia e where e.esFacultad='Y' order by e.nombre");
		facultadItem = new SelectItem[facultadesUN.size()];
		for (int i = 0; i < facultadesUN.size(); i++) {
			Dependencia dd = (Dependencia) facultadesUN.get(i);
			facultadItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void cargarObjetosDominios() {
		// Modalidad
		if (curso.getModalidadEcp() != null) {
			if (curso.getModalidadEcp().equals("A"))
				modalidadEcp = "Abierta";
			else if (curso.getModalidadEcp().equals("C"))
				modalidadEcp = "Cerrado";
		} else
			System.out.println("Modalidad Extension NULA");

		// Submodalidad
		String consulta = "select dd from Dominio d, DominioDetalle dd "
				+ "where d.id = dd.identificador.id "
				+ "and d.tipo = 'SUBMODALIDAD_ECP' "
				+ "and dd.identificador.tipo = '" + curso.getClaseEvento()
				+ "'";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		if (lista.size() > 0)
			claseEvento = ((DominioDetalle) lista.get(0)).getDescripcion();

		// Tipo de evento
		String consulta2 = "select dd from Dominio d, DominioDetalle dd "
				+ "where d.id = dd.identificador.id "
				+ "and d.tipo = 'TEVENTO' " + "and dd.identificador.tipo = '"
				+ curso.getClaseEventoExt() + "'";
		List lista2 = servicioGeneral.obtenerObjetos(consulta2);

		if (lista2.size() > 0)
			claseEventoExt = ((DominioDetalle) lista2.get(0)).getDescripcion();
	}

	public void cargarListaObjetivos() {
		String hql = "select oe from ObjetivoEspecifico oe where oe.proyecto.id = "
				+ curso.getId();
		List lista = servicioGeneral.obtenerObjetos(hql);

		if (lista.size() != 0) {
			for (int i = 0; i < lista.size(); i++)
				listaObjetivos.add((ObjetivoEspecifico) lista.get(i));
		}
	}

	public void cargarEstadosCurso() {
		String hql = "select pp from EstadoProyecto pp where pp.id in ('A','AP','PB','CN','F')";
		List lista = servicioGeneral.obtenerObjetos(hql);

		if (lista.size() != 0 && listaEstadosProyectoItem != null) {
			for (int i = 0; i < lista.size(); i++) {
				EstadoProyecto estado = (EstadoProyecto) lista.get(i);
				listaEstadosProyectoItem.add(new SelectItem(estado.getId(),
						estado.getNombre()));
			}
		}
	}

	/*public void crearListaGrupos() {
		System.out.println("creando lista cursos");
		listaGruposItems = new ArrayList<SelectItem>();
		for (int i = 0; i < this.curso.getNumeroGrupos(); i++) {
			listaGruposItems.add(new SelectItem(i + 1));
		}

	}*/

	public void adicionarGrupo() {

		GruposCursosECP gruCur = new GruposCursosECP();
//		idGruCur.setGrupo(grupoSel);
//		idGruCur.setProyecto(curso);
		//gruCur.setId(idGruCur);
		gruCur.setProyecto(curso);
		gruCur.setGrupoCurso(grupoSel);
		gruCur.setDescripcionGrupo(descripcionGrupo);
		gruCur.setNumeroAsistentesGrupo(numGrupoInscritos);

		if (grupoSel != 0 && numGrupoInscritos != 0) {
			if (!listaGruposCurso.contains(gruCur)) {
				listaGruposCurso.add(gruCur);
				descripcionGrupo = "";
				numGrupoInscritos = 1;
				grupoSel = 0;
				listaGruposHorarioItems.add(new SelectItem(gruCur.getGrupoCurso()));
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										"La información del grupo ya se encuentra registrada",
										"La información del grupo ya se encuentra registrada"));
			}
		}else{
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_FATAL,
							"Por favor seleccione un grupo e ingrese la información del número de asistentes.",
							"Por favor seleccione un grupo e ingrese la información del número de asistentes."));
			
		}

	}


	public void eliminarGrupo() {
		
		
//		if(grupoBorrarSeleccionado.getMateriales().size() == 0 || sesionSeleccionada.getMateriales() == null)
//    	{	
			listaGruposCurso.remove(grupoBorrarSeleccionado);
    		
    		for(int i=0; i<listaGruposHorarioItems.size();i++)
        	{
        		if(listaGruposHorarioItems.get(i).getValue().equals(grupoBorrarSeleccionado.getGrupoCurso()))
        			listaGruposHorarioItems.remove(i);
        	}
	    	
//    	}
//    	else
//    	{
//    		System.out.println("Error la eliminar sesion. Eliminar primero los materiales");
//    		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "No se elimino la sesion", "Antes de eliminar la sesion "+sesionSeleccionada.getNumero()+" elimine los materiales asociados"));
//    	}		
		
		grupoBorrarSeleccionado = new GruposCursosECP();
		
	}
	
	public void adicionarHorario(){
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
		if(!horario.equals("0") && grupoHorario != 0 && horaInicio != null && horafinal != null){
			GruposHorariosECP grupoHorarioECP = new GruposHorariosECP();
//			IdHorariosGruposECP idGrupoHorario = new IdHorariosGruposECP();
//			idGrupoHorario.setDia(horario);
//			idGrupoHorario.setGrupo(String.valueOf(grupoHorario));
//			idGrupoHorario.setProyecto(curso);
			grupoHorarioECP.setDia(horario);
			GruposCursosECP gc = new GruposCursosECP();
			gc.setGrupoCurso(grupoHorario);
			grupoHorarioECP.setGrupo(gc);
			grupoHorarioECP.setProyecto(curso);
			grupoHorarioECP.setHoraInicio(sdf.format(horaInicio));
			grupoHorarioECP.setHoraFin(sdf.format(horafinal));
			if(!listaGrupoHorarioDia.contains(grupoHorarioECP)){
				listaGrupoHorarioDia.add(grupoHorarioECP);
			}else{
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_FATAL,
								"La información del grupo para ese día ya se encuentra registrada.",
								"La información del grupo para ese día ya se encuentra registrada."));
			}
		}else{
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_FATAL,
							"Por favor seleccione un grupo e ingrese la información del horario por día.",
							"Por favor seleccione un grupo e ingrese la información del horario por día."));
		}
		
	}
	
	public void eliminarGrupoHorario(){
		listaGrupoHorarioDia.remove(grupoHorarioBorrarSeleccionado);
		grupoHorarioBorrarSeleccionado = new GruposHorariosECP();
	}
	
	

	public Proyecto getCurso() {
		return curso;
	}

	public void setCurso(Proyecto curso) {
		this.curso = curso;
	}

	public List getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(List listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

	public String getModalidadEcp() {
		return modalidadEcp;
	}

	public void setModalidadEcp(String modalidadEcp) {
		this.modalidadEcp = modalidadEcp;
	}

	public String getClaseEvento() {
		return claseEvento;
	}

	public void setClaseEvento(String claseEvento) {
		this.claseEvento = claseEvento;
	}

	public String getClaseEventoExt() {
		return claseEventoExt;
	}

	public void setClaseEventoExt(String claseEventoExt) {
		this.claseEventoExt = claseEventoExt;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHorafinal() {
		return horafinal;
	}

	public void setHorafinal(Date horafinal) {
		this.horafinal = horafinal;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List<Sede> getSedesUN() {
		return sedesUN;
	}

	public void setSedesUN(List<Sede> sedesUN) {
		this.sedesUN = sedesUN;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public List<SelectItem> getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}

	public void setListaEstadosProyectoItem(
			List<SelectItem> listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}

	public List<SelectItem> getListaGruposItems() {
		return listaGruposItems;
	}

	public void setListaGruposItems(List<SelectItem> listaGruposItems) {
		this.listaGruposItems = listaGruposItems;
	}

	public int getGrupoSel() {
		return grupoSel;
	}

	public void setGrupoSel(int grupoSel) {
		this.grupoSel = grupoSel;
	}

	public int getNumGrupoInscritos() {
		return numGrupoInscritos;
	}

	public void setNumGrupoInscritos(int numGrupoInscritos) {
		this.numGrupoInscritos = numGrupoInscritos;
	}

	public List<SelectItem> getListaDiasItems() {
		return listaDiasItems;
	}

	public void setListaDiasItems(List<SelectItem> listaDiasItems) {
		this.listaDiasItems = listaDiasItems;
	}

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public List<GruposCursosECP> getListaGruposCurso() {
		return listaGruposCurso;
	}

	public void setListaGruposCurso(List<GruposCursosECP> listaGruposCurso) {
		this.listaGruposCurso = listaGruposCurso;
	}

	public GruposCursosECP getGrupoBorrarSeleccionado() {
		return grupoBorrarSeleccionado;
	}

	public void setGrupoBorrarSeleccionado(
			GruposCursosECP grupoBorrarSeleccionado) {
		this.grupoBorrarSeleccionado = grupoBorrarSeleccionado;
	}

	public int getGrupoHorario() {
		return grupoHorario;
	}

	public void setGrupoHorario(int grupoHorario) {
		this.grupoHorario = grupoHorario;
	}

	public List<SelectItem> getListaGruposHorarioItems() {
		return listaGruposHorarioItems;
	}

	public void setListaGruposHorarioItems(List<SelectItem> listaGruposHorarioItems) {
		this.listaGruposHorarioItems = listaGruposHorarioItems;
	}

	public List<GruposHorariosECP> getListaGrupoHorarioDia() {
		return listaGrupoHorarioDia;
	}

	public void setListaGrupoHorarioDia(List<GruposHorariosECP> listaGrupoHorarioDia) {
		this.listaGrupoHorarioDia = listaGrupoHorarioDia;
	}

	public GruposHorariosECP getGrupoHorarioBorrarSeleccionado() {
		return grupoHorarioBorrarSeleccionado;
	}

	public void setGrupoHorarioBorrarSeleccionado(
			GruposHorariosECP grupoHorarioBorrarSeleccionado) {
		this.grupoHorarioBorrarSeleccionado = grupoHorarioBorrarSeleccionado;
	}

}