package co.edu.unal.hermes.vista.encuesta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EncuestaSoporte;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;


public class ManejadorConsultarEncuesta extends ManejadorBase implements Serializable {

	EncuestaSoporte encuesta;
	
	public SelectItem[] atencionItems; //{new SelectItem("3", "Excelente"), new SelectItem("2", "Buena"), new SelectItem("1", "Debe mejorar") };
	public SelectItem[] moduloItems = { new SelectItem("SI", "SI"),new SelectItem("NO", "NO") };
	private SelectItem[] sedesItems;
	public SelectItem[] procesoItems;
	
	private String DOMINIO_ATENCION = "ATENCION_SOPORTE";
	private String DOMINIO_PROCESO = "PROCESO_SOPORTE";
	
	private List listaResultadosEncuesta;
	private int numeroResultados = 0;
	
	@SuppressWarnings("deprecation")
	public ManejadorConsultarEncuesta() {
		
			
		//Cargar lista de resultados
		listaResultadosEncuesta = new ArrayList();
		
		listaResultadosEncuesta = servicioGeneral.obtenerObjetos(EncuestaSoporte.class,"from EncuestaSoporte e order by e.id desc" );
		if(listaResultadosEncuesta.size()>0){
			numeroResultados = listaResultadosEncuesta.size();
			/*for(int i=0; i<listaResultadosEncuesta.size();i++){
				
			}*/
		}				
	}
	
	//
	public String verTodosResultados()
    {   
		sesion.removeAttribute("ManejadorConsultarEncuesta");
	
		return "ConsultarEncuesta";
			
    }
	
	public void cargarListas(){
		//Cargar sedes
		if(sedesItems == null){
			//Sedes
			List<Sede> sedes = servicioGeneral.obtenerObjetos("select d from Sede d order by d.nombre");
			if(sedes.size()>0){
				sedesItems = new SelectItem[sedes.size()];
				for (int i = 0; i < sedes.size(); i++) {
					Sede dd = (Sede) sedes.get(i);
					sedesItems[i] = new SelectItem(dd.getId(), dd.getNombre());
					dd = null;
				}
				
				encuesta.setIdSede(sedes.get(0));
				
			}else{
				sedesItems = new SelectItem[0];
			}
			
		}
		
		
		//Cargar Atención
		List listaPrograma = new ArrayList<DominioDetalle>();
		listaPrograma = servicioGeneral.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_ATENCION + "' order by dd.estado");
		atencionItems = new SelectItem[listaPrograma.size()];
		for (int i = 0; i < listaPrograma.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaPrograma.get(i);
			atencionItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
			
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
	
	///GET- SET
	public EncuestaSoporte getEncuesta() {
		return encuesta;
	}


	public void setEncuesta(EncuestaSoporte encuesta) {
		this.encuesta = encuesta;
	}


	public SelectItem[] getAtencionItems() {
		return atencionItems;
	}


	public void setAtencionItems(SelectItem[] atencionItems) {
		this.atencionItems = atencionItems;
	}


	public SelectItem[] getModuloItems() {
		return moduloItems;
	}


	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}


	public SelectItem[] getSedesItems() {
		return sedesItems;
	}


	public void setSedesItems(SelectItem[] sedesItems) {
		this.sedesItems = sedesItems;
	}


	public SelectItem[] getProcesoItems() {
		return procesoItems;
	}


	public void setProcesoItems(SelectItem[] procesoItems) {
		this.procesoItems = procesoItems;
	}


	public List getListaResultadosEncuesta() {
		return listaResultadosEncuesta;
	}


	public void setListaResultadosEncuesta(List listaResultadosEncuesta) {
		this.listaResultadosEncuesta = listaResultadosEncuesta;
	}


	public int getNumeroResultados() {
		return numeroResultados;
	}


	public void setNumeroResultados(int numeroResultados) {
		this.numeroResultados = numeroResultados;
	}
	
	
	
}