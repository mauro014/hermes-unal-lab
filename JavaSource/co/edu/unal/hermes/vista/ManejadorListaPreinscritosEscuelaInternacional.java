package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import co.edu.unal.hermes.modelo.InscripcionEscuelaInternacional;
import co.edu.unal.hermes.modelo.InscripcionEscuelaInternacionalDataModel;
import co.edu.unal.hermes.modelo.ModuloCursoEscuela;


public class ManejadorListaPreinscritosEscuelaInternacional extends ManejadorBase
{
	List listaPreinscritos;
	ModuloCursoEscuela cursoActual;
	String idCurso;
	InscripcionEscuelaInternacional estudianteSeleccionado;
	InscripcionEscuelaInternacionalDataModel seleccionadoDataModel;
	List listaEstadosPreinscripcion;
	
	public ManejadorListaPreinscritosEscuelaInternacional()
	{
		idCurso = "1007";
		cargarCursoActual();
		cargarListaPreinscritos();
		cargarEstadosPreinscripcion();
		
	}
	
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for(int i=0; i < header.getPhysicalNumberOfCells();i++) 
		{
			sheet.setColumnWidth(i, 7000);
			header.getCell(i).setCellStyle(cellStyle);
			
			if(header.getCell(i).getStringCellValue().equals("ACEPTADO"))
			{
				for (int j = 1; j <= sheet.getLastRowNum(); j++) 
				{
					//ystem.out.println("I : "+i+" - J : "+j+" - Valor: "+sheet.getRow(j).getCell(i).getStringCellValue());
					if(sheet.getRow(j).getCell(i).getStringCellValue().equals("true"))
						sheet.getRow(j).getCell(i).setCellValue("SI");		
					else if(sheet.getRow(j).getCell(i).getStringCellValue().equals("false"))
						sheet.getRow(j).getCell(i).setCellValue("NO");
				}
			}
		}
		
		
		
	}
	
	public void guardar()
	{
		if(listaPreinscritos != null)
		{
			for (int i = 0; i < listaPreinscritos.size(); i++) 
			{
				InscripcionEscuelaInternacional iei = (InscripcionEscuelaInternacional) listaPreinscritos.get(i);
				servicioGeneral.guardarObjeto(iei);
			}
			
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"mensajeGrowl",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"",
							"Se ha actualizado la informacion del curso con exito"));
			
			estudianteSeleccionado = null;
		}
	}
	
	public String regresar() {
		sesion.removeAttribute("ManejadorListaPreinscritosEscuelaInternacional");
		return "escuelaInternacionalLista";
	}
	
	public void cargarEstadosPreinscripcion()
	{
		listaEstadosPreinscripcion = new Vector();
		listaEstadosPreinscripcion.add(new SelectItem("PREINSCRITO", "PREINSCRITO"));
		listaEstadosPreinscripcion.add(new SelectItem("ACEPTADO", "ACEPTADO"));
		listaEstadosPreinscripcion.add(new SelectItem("CANCELA SIN PERDIDA", "CANCELA SIN PERDIDA"));
		listaEstadosPreinscripcion.add(new SelectItem("CANCELA CON PERDIDA", "CANCELA CON PERDIDA"));
		listaEstadosPreinscripcion.add(new SelectItem("CANCELA OTRO", "CANCELA OTRO"));
	}
	
	public String validarCorreoUnal(String correo)
	{
		if(correo.contains("@"))
		{
			return correo;
		}
		else
			return correo.concat("@unal.edu.co");
	}
	
	public void cargarListaPreinscritos()
	{
		listaPreinscritos = new ArrayList<InscripcionEscuelaInternacional>();
		
		if(idCurso != null)
		{
			String consulta = "select pp from InscripcionEscuelaInternacional pp where pp.curso = "+Long.parseLong(idCurso);
			List lista = servicioGeneral.obtenerObjetos(consulta);
			
			if(lista.size() > 0)
			{
				for (int i = 0; i < lista.size(); i++)
				{
					InscripcionEscuelaInternacional preinscrito = (InscripcionEscuelaInternacional) lista.get(i);
					preinscrito.setCorreo(validarCorreoUnal(preinscrito.getCorreo()));
					listaPreinscritos.add(preinscrito);
				}	
			}
			
			seleccionadoDataModel = new InscripcionEscuelaInternacionalDataModel(listaPreinscritos);
		}
	}
	
	public void cargarCursoActual()
	{
		if(idCurso != null)
		{
			String consulta = "select pp from ModuloCursoEscuela pp where pp.id like '"+idCurso+"'";
			List lista = servicioGeneral.obtenerObjetos(consulta);
			
			if(lista.size() > 0)
				cursoActual = (ModuloCursoEscuela) lista.get(0);
			
		}
	}
	
	public void onRowSelect(SelectEvent event) {  
		
		estudianteSeleccionado = (InscripcionEscuelaInternacional) event.getObject();
		estudianteSeleccionado.cargarStreamedContent();
    }  
  
    public void onRowUnselect(UnselectEvent event) {
    	estudianteSeleccionado = (InscripcionEscuelaInternacional) event.getObject();
    }
	
	public ModuloCursoEscuela getCursoActual() {
		return cursoActual;
	}

	public void setCursoActual(ModuloCursoEscuela cursoActual) {
		this.cursoActual = cursoActual;
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

	public InscripcionEscuelaInternacional getEstudianteSeleccionado() {
		return estudianteSeleccionado;
	}

	public void setEstudianteSeleccionado(
			InscripcionEscuelaInternacional estudianteSeleccionado) {
		this.estudianteSeleccionado = estudianteSeleccionado;
	}

	public InscripcionEscuelaInternacionalDataModel getSeleccionadoDataModel() {
		return seleccionadoDataModel;
	}

	public void setSeleccionadoDataModel(
			InscripcionEscuelaInternacionalDataModel seleccionadoDataModel) {
		this.seleccionadoDataModel = seleccionadoDataModel;
	}

	public List getListaEstadosPreinscripcion() {
		return listaEstadosPreinscripcion;
	}

	public void setListaEstadosPreinscripcion(List listaEstadosPreinscripcion) {
		this.listaEstadosPreinscripcion = listaEstadosPreinscripcion;
	}
	
	
	
}