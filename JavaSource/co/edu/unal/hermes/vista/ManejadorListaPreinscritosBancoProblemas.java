package co.edu.unal.hermes.vista;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import co.edu.unal.hermes.modelo.ArchivosPreinscripcionECP;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.GruposCursosECP;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PreinscripcionECPDataModel;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorListaPreinscritosBancoProblemas extends ManejadorBase {
	List listaPreinscritos;
	Proyecto cursoActual;
	String idCurso;
	Preinscripcion_ECP estudianteSeleccionado;
	PreinscripcionECPDataModel seleccionadoDataModel;
	PreinscripcionECPDataModel listaEstFiltrada;
	List listaEstadosPreinscripcion;

	List listaArchivosAdjuntos;
	ArchivosPreinscripcionECP archivoSeleccionado;

	private String generoDesc;
	private String ocupacionDesc;
	private String vinculacionDesc;
	private String sectorEmpresaDesc;
	private String naturalezaEmpresaDesc;

	public ManejadorListaPreinscritosBancoProblemas() {
		//idCurso = "34589";
		//idCurso = "39232";//2017
		idCurso = "42585";//proyecto 2018
		//cursoActual = (Proyecto) sesion.getAttribute("cursoECP");
		cargarCursoActual();
		cargarListaPreinscritos();
		cargarEstadosPreinscripcion();

		listaArchivosAdjuntos = new ArrayList<ArchivosPreinscripcionECP>();

	}

	public void cargarArchivosAdjuntos() {
		listaArchivosAdjuntos = new ArrayList<ArchivosPreinscripcionECP>();

		String cons = "select pp from ArchivosPreinscripcionECP pp where  pp.preinscripcionECP.id_pre = "
				+ estudianteSeleccionado.getId_pre();
		List lista = servicioGeneral.obtenerObjetos(cons);

		if (lista.size() != 0) {
			for (int i = 0; i < lista.size(); i++) {

				ArchivosPreinscripcionECP arc = (ArchivosPreinscripcionECP) lista
						.get(i);
				listaArchivosAdjuntos.add(arc);
			}
		}
	}

	public void cargarListaPreinscritosOriginal() {
		listaPreinscritos = new ArrayList<Preinscripcion_ECP>();

		if (cursoActual != null) {
			String consulta = "select pp from Preinscripcion_ECP pp where pp.curso.id = "
					+ cursoActual.getId();
			List lista = servicioGeneral.obtenerObjetos(consulta);

			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					Preinscripcion_ECP preinscritoECP = (Preinscripcion_ECP) lista
							.get(i);
					preinscritoECP.getInvestigador().setEmail(
							(validarCorreoUnal(preinscritoECP.getInvestigador()
									.getEmail())));
					
				
					listaPreinscritos.add(preinscritoECP);
				}
			}

			seleccionadoDataModel = new PreinscripcionECPDataModel(
					listaPreinscritos);
		}
	}

	public void cargarListaPreinscritos() {
		listaPreinscritos = new ArrayList<Preinscripcion_ECP>();

		if (cursoActual != null) {

			Persona coordinadorCurso = (Persona) sesion.getAttribute("persona");

			String consultaGruposCurso = "select pp from GruposCursosECP pp where pp.proyecto.id = "
					+ cursoActual.getId()
					+ " and pp.idCoordinadorGrupo like '%"
					+ coordinadorCurso.getId().getDocumento()
					+ "%'";
			List listaGrupos = servicioGeneral
					.obtenerObjetos(consultaGruposCurso);

			if (listaGrupos != null && listaGrupos.size() > 0) {

				for (int j = 0; j < listaGrupos.size(); j++) {
					GruposCursosECP grCur = (GruposCursosECP) listaGrupos
							.get(j);

					String consulta = "select pp from Preinscripcion_ECP pp where pp.curso.id = "
							+ cursoActual.getId()+" order by  pp.id_pre";
					List lista = servicioGeneral.obtenerObjetos(consulta);

					if (lista.size() > 0) {
						for (int i = 0; i < lista.size(); i++) {
							Preinscripcion_ECP preinscritoECP = (Preinscripcion_ECP) lista
									.get(i);
							preinscritoECP.getInvestigador().setEmail(
									(validarCorreoUnal(preinscritoECP
											.getInvestigador().getEmail())));
							
							String cons = "select #idArchivo pp.idArchivo from ArchivosPreinscripcionECP pp where  pp.preinscripcionECP.id_pre = "
									+ preinscritoECP.getId_pre();

							List listaArchivos = servicioGeneral.obtenerObjetosLimitado(ArchivosPreinscripcionECP.class, cons);

							if (!esListaVacia(listaArchivos)) {
								preinscritoECP.setTieneArchivos(true);
							}
							
							List<DominioDetalle> listaRolRegistro = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
					                        + "TIPO_PARTICIPANTE_EXT_SOL_2016" + "' and dd.identificador.tipo = '"+preinscritoECP.getTipo_vinculacion()+"' order by dd.descripcion");

							if (!esListaVacia(listaRolRegistro)) {
								DominioDetalle dd = listaRolRegistro.get(0);
								preinscritoECP.setTipoVinculacionNombre(dd.getDescripcion());
							}
							
							listaPreinscritos.add(preinscritoECP);
						}
					}

					seleccionadoDataModel = new PreinscripcionECPDataModel(
							listaPreinscritos);
				}

			} else {
				String consulta = "select pp from Preinscripcion_ECP pp where pp.curso.id = "
						+ cursoActual.getId();
				List lista = servicioGeneral.obtenerObjetos(consulta);

				if (lista.size() > 0) {
					for (int i = 0; i < lista.size(); i++) {
						Preinscripcion_ECP preinscritoECP = (Preinscripcion_ECP) lista
								.get(i);
						preinscritoECP.getInvestigador().setEmail(
								(validarCorreoUnal(preinscritoECP
										.getInvestigador().getEmail())));
						
						String cons = "select #idArchivo pp.idArchivo from ArchivosPreinscripcionECP pp where  pp.preinscripcionECP.id_pre = "
								+ preinscritoECP.getId_pre();
						
						List listaArchivos = servicioGeneral.obtenerObjetosLimitado(ArchivosPreinscripcionECP.class, cons);

						if (!esListaVacia(listaArchivos)) {
							preinscritoECP.setTieneArchivos(true);
						}
						
						List<DominioDetalle> listaRolRegistro = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				                        + "TIPO_PARTICIPANTE_EXT_SOL_2016" + "' and dd.identificador.tipo = '"+preinscritoECP.getTipo_vinculacion()+"' order by dd.descripcion");

						if (!esListaVacia(listaRolRegistro)) {
							DominioDetalle dd = listaRolRegistro.get(0);
							preinscritoECP.setTipoVinculacionNombre(dd.getDescripcion());
						}
						
						listaPreinscritos.add(preinscritoECP);
					}
				}

				seleccionadoDataModel = new PreinscripcionECPDataModel(
						listaPreinscritos);
			}

		}
	}

	public void cargarCursoActual() {
		if (idCurso != null) {
			String consulta = "select pp from Proyecto pp where pp.id like '"
					+ idCurso + "'";
			List lista = servicioGeneral.obtenerObjetos(consulta);

			if (lista.size() > 0)
				cursoActual = (Proyecto) lista.get(0);

		}
	}

	public void cargarEstadosPreinscripcion() {
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'ESTADOS_BANCO_PROBLEMAS' order by dd.descripcion";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		listaEstadosPreinscripcion = new ArrayList<SelectItem>();

		if (lista.size() > 0) {

			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				listaEstadosPreinscripcion
						.add(new SelectItem(dominio.getIdentificador()
								.getTipo(), dominio.getDescripcion()));
			}
		} else {
			listaEstadosPreinscripcion.add(new SelectItem("0", " - "));
		}

	}

	public void guardar() {
		if (listaPreinscritos != null) {
			for (int i = 0; i < listaPreinscritos.size(); i++) {
				Preinscripcion_ECP pre = (Preinscripcion_ECP) listaPreinscritos
						.get(i);
				servicioGeneral.guardarObjeto(pre);
			}

			FacesContext
					.getCurrentInstance()
					.addMessage(
							"mensajeGrowl",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "",
									"Se ha actualizado la informacion del curso con exito"));

			estudianteSeleccionado = null;
		}
	}

	public String regresar() {
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		return "volverlistaCursosECP";
	}

	public String validarCorreoUnal(String correo) {
		if (correo.contains("@")) {
			return correo;
		} else
			return correo.concat("@unal.edu.co");
	}

	public void cargarDescripciones() {
		DominioDetalle ocupacion_atributo = new DominioDetalle();
		DominioDetalle sector_atributo = new DominioDetalle();
		DominioDetalle naturaleza_atributo = new DominioDetalle();
		DominioDetalle tipo_vinculacion_atributo = new DominioDetalle();

		if (estudianteSeleccionado != null) {
			String cons_ocupacion = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ estudianteSeleccionado.getOcupacion() + "%'";
			List lista_ocupacion = servicioGeneral
					.obtenerObjetos(cons_ocupacion);
			if (lista_ocupacion.size() != 0) {
				ocupacion_atributo = (DominioDetalle) lista_ocupacion.get(0);
				ocupacionDesc = ocupacion_atributo.getDescripcion();
			}

			String cons_sector = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ estudianteSeleccionado.getSector_empresa() + "%'";
			List lista_sector = servicioGeneral.obtenerObjetos(cons_sector);
			if (lista_sector.size() != 0) {
				sector_atributo = (DominioDetalle) lista_sector.get(0);
				sectorEmpresaDesc = sector_atributo.getDescripcion();
			}

			String cons_naturaleza = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ estudianteSeleccionado.getTipo_empresa() + "%'";
			List lista_naturaleza = servicioGeneral
					.obtenerObjetos(cons_naturaleza);
			if (lista_naturaleza.size() != 0) {
				naturaleza_atributo = (DominioDetalle) lista_naturaleza.get(0);
				naturalezaEmpresaDesc = naturaleza_atributo.getDescripcion();
			}

			String cons_tpvinculacion = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ estudianteSeleccionado.getTipo_vinculacion() + "%'";
			List lista_tpVinculacion = servicioGeneral
					.obtenerObjetos(cons_tpvinculacion);
			if (lista_tpVinculacion.size() != 0) {
				tipo_vinculacion_atributo = (DominioDetalle) lista_tpVinculacion
						.get(0);
				vinculacionDesc = tipo_vinculacion_atributo.getDescripcion();
			}

			if (estudianteSeleccionado.getInvestigador().getGenero()
					.equals("M"))
				generoDesc = "Masculino";
			else if (estudianteSeleccionado.getInvestigador().getGenero()
					.equals("F"))
				generoDesc = "Femenino";
		} else
			System.out
					.println("cargarDescripciones() : estudianteSeleccionado NULO");

	}

	public void onRowSelect(SelectEvent event) {

		estudianteSeleccionado = (Preinscripcion_ECP) event.getObject();
		cargarDescripciones();
		cargarArchivosAdjuntos();
		// estudianteSeleccionado.cargarStreamedContent();
	}

	public void onRowUnselect(UnselectEvent event) {
		estudianteSeleccionado = (Preinscripcion_ECP) event.getObject();
		cargarDescripciones();
		cargarArchivosAdjuntos();
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			sheet.setColumnWidth(i, 7000);
			header.getCell(i).setCellStyle(cellStyle);

			if (header.getCell(i).getStringCellValue().equals("ACEPTADO")) {
				for (int j = 1; j <= sheet.getLastRowNum(); j++) {
					// ystem.out.println("I : "+i+" - J : "+j+" - Valor: "+sheet.getRow(j).getCell(i).getStringCellValue());
					if (sheet.getRow(j).getCell(i).getStringCellValue()
							.equals("true"))
						sheet.getRow(j).getCell(i).setCellValue("SI");
					else if (sheet.getRow(j).getCell(i).getStringCellValue()
							.equals("false"))
						sheet.getRow(j).getCell(i).setCellValue("NO");
				}
			}
		}
	}

	public void descargarArchivo() {

		// String path =
		// RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"+archivoSeleccionado.getIdArchivo();
		//
		// //String path =
		// "E://HER_EXT_ARC_PREINS//"+archivoSeleccionado.getNombreArchivo();
		//
		// FacesContext ctx = FacesContext.getCurrentInstance();
		// //String path;
		// // path = ArchivoLaboratorio.DIRECTORIO_ARCHIVOS;
		// // path += archivoLaboratorioSeleccionado.getId();
		// System.out.println("descargarArchivo path:" + path);
		// File ficheroXLS = new File(path);
		// FileInputStream fis;
		// try {
		// fis = new FileInputStream(ficheroXLS);
		// //fis = new FileInputStream();
		// //fis = (FileInputStream)
		// archivoSeleccionado.getArchivoInputStream();
		// byte[] bytes = new byte[1000];
		// int read = 0;
		// if (!ctx.getResponseComplete()) {
		//
		// String fileName = archivoSeleccionado
		// .getNombreArchivo();
		// String extension = fileName.substring(
		// fileName.lastIndexOf("."), fileName.length());
		// System.out.println("descargarArchivo extension:" + extension);
		// // String contentType = "application/msword";
		// String contentType = "text/plain";
		// System.out.println("descargarArchivo contentType:"
		// + contentType);
		// HttpServletResponse response = (HttpServletResponse) ctx
		// .getExternalContext().getResponse();
		// response.setContentType(contentType);
		// response.setHeader("Content-Disposition",
		// "attachment;filename=\"" + fileName + "\"");
		// ServletOutputStream out = response.getOutputStream();
		// while ((read = fis.read(bytes)) != -1) {
		// out.write(bytes, 0, read);
		// }
		// out.flush();
		// out.close();
		// ctx.responseComplete();
		// }

		try {
			descargarArchivoGenerico("HER_EXT_ARC_PREINS", archivoSeleccionado
					.getIdArchivo().toString(),
					archivoSeleccionado.getNombreArchivo());

		} catch (Exception e) {
			e.printStackTrace();
			mensajeError("Error al descargar el archivo.");
		}
	}

	public void descargarArchivo2() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"
				+ archivoSeleccionado.getIdArchivo();
		String path = directorio + "//"
				+ archivoSeleccionado.getNombreArchivo();

		// String path =
		// "E://HER_EXT_ARC_PREINS//"+archivoSeleccionado.getIdArchivo()+"//"+archivoSeleccionado.getNombreArchivo();

		File ficheroXLS = new File(path);

		FileInputStream fis;
		try {
			fis = new FileInputStream(ficheroXLS);

			byte[] bytes = new byte[1000];
			int read = 0;

			if (!ctx.getResponseComplete()) {
				String fileName = ficheroXLS.getName();
				String contentType = "application/msword";
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();

				response.setContentType(contentType);

				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + fileName + "\"");

				ServletOutputStream out = response.getOutputStream();

				while ((read = fis.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.flush();
				out.close();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public List getListaPreinscritos() {
		return listaPreinscritos;
	}

	public void setListaPreinscritos(List listaPreinscritos) {
		this.listaPreinscritos = listaPreinscritos;
	}

	public PreinscripcionECPDataModel getSeleccionadoDataModel() {
		return seleccionadoDataModel;
	}

	public void setSeleccionadoDataModel(
			PreinscripcionECPDataModel seleccionadoDataModel) {
		this.seleccionadoDataModel = seleccionadoDataModel;
	}

	public List getListaEstadosPreinscripcion() {
		return listaEstadosPreinscripcion;
	}

	public void setListaEstadosPreinscripcion(List listaEstadosPreinscripcion) {
		this.listaEstadosPreinscripcion = listaEstadosPreinscripcion;
	}

	public Preinscripcion_ECP getEstudianteSeleccionado() {
		return estudianteSeleccionado;
	}

	public void setEstudianteSeleccionado(
			Preinscripcion_ECP estudianteSeleccionado) {
		this.estudianteSeleccionado = estudianteSeleccionado;
	}

	public Proyecto getCursoActual() {
		return cursoActual;
	}

	public void setCursoActual(Proyecto cursoActual) {
		this.cursoActual = cursoActual;
	}

	public String getGeneroDesc() {
		return generoDesc;
	}

	public void setGeneroDesc(String generoDesc) {
		this.generoDesc = generoDesc;
	}

	public String getOcupacionDesc() {
		return ocupacionDesc;
	}

	public void setOcupacionDesc(String ocupacionDesc) {
		this.ocupacionDesc = ocupacionDesc;
	}

	public String getVinculacionDesc() {
		return vinculacionDesc;
	}

	public void setVinculacionDesc(String vinculacionDesc) {
		this.vinculacionDesc = vinculacionDesc;
	}

	public String getSectorEmpresaDesc() {
		return sectorEmpresaDesc;
	}

	public void setSectorEmpresaDesc(String sectorEmpresaDesc) {
		this.sectorEmpresaDesc = sectorEmpresaDesc;
	}

	public String getNaturalezaEmpresaDesc() {
		return naturalezaEmpresaDesc;
	}

	public void setNaturalezaEmpresaDesc(String naturalezaEmpresaDesc) {
		this.naturalezaEmpresaDesc = naturalezaEmpresaDesc;
	}

	public List getListaArchivosAdjuntos() {
		return listaArchivosAdjuntos;
	}

	public void setListaArchivosAdjuntos(List listaArchivosAdjuntos) {
		this.listaArchivosAdjuntos = listaArchivosAdjuntos;
	}

	public ArchivosPreinscripcionECP getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(
			ArchivosPreinscripcionECP archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public PreinscripcionECPDataModel getListaEstFiltrada() {
		return listaEstFiltrada;
	}

	public void setListaEstFiltrada(PreinscripcionECPDataModel listaEstFiltrada) {
		this.listaEstFiltrada = listaEstFiltrada;
	}

}