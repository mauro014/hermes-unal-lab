package co.edu.unal.hermes.vista.laboratorios;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogLaboratorios;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaHistoricosLaboratorios extends ManejadorBase {

	Laboratorio laboratorioActual = new Laboratorio();
	Long idLab;
	List<LaboratorioLogLaboratorios> listaHistoricoEstadoLaboratorio;
	
	public ManejadorConsultaHistoricosLaboratorios() {
		super();
	
		try{
		idLab = (Long) sesion.getAttribute("lab_id");
		laboratorioActual = (Laboratorio) sesion.getAttribute("lab");
		buscarHistoricoLaboratorio();
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		wb.setSheetName(0, "HISTORICO CAMBIOS LABORATORIO");
		int numeroColumnas = header.getPhysicalNumberOfCells();
		// fija el estilo al encabezado
		for (int i = 0; i < numeroColumnas; i++) {
			header.getCell(i).setCellStyle(cellStyle);
		}

		// Inmovilizar fila superior:
		sheet.createFreezePane(0, 1);

	}

	public void buscarHistoricoLaboratorio() {
		listaHistoricoEstadoLaboratorio = servicioGeneral.obtenerHistoricoEstadosLaboratorio(laboratorioActual.getId());
	}
	
	public String volverAdministrarLaboratorios()
	{
		sesion.removeAttribute("manejadorConsultaHistoricosLaboratorios");
		return "AdministrarLaboratorios";
	}

	public Laboratorio getLaboratorioActual() {
		return laboratorioActual;
	}

	public void setLaboratorioActual(Laboratorio laboratorioActual) {
		this.laboratorioActual = laboratorioActual;
	}

	public Long getIdLab() {
		return idLab;
	}

	public void setIdLab(Long idLab) {
		this.idLab = idLab;
	}

	public List<LaboratorioLogLaboratorios> getListaHistoricoEstadoLaboratorio() {
		return listaHistoricoEstadoLaboratorio;
	}

	public void setListaHistoricoEstadoLaboratorio(
			List<LaboratorioLogLaboratorios> listaHistoricoEstadoLaboratorio) {
		this.listaHistoricoEstadoLaboratorio = listaHistoricoEstadoLaboratorio;
	}

}
