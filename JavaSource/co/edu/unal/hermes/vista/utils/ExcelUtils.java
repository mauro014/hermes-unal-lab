package co.edu.unal.hermes.vista.utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public static XSSFWorkbook getExcelFromResulSet(String[] header, List<Object[]> data) throws SQLException{

		XSSFWorkbook workbook = new XSSFWorkbook();
		
	    XSSFFont boldFont = workbook.createFont();
	    boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    
	    XSSFSheet sheet = workbook.createSheet("Resultados consulta");
	    
	    XSSFRow titleRow = sheet.createRow(0);
	    
	    for (int colIndex = 0; colIndex < header.length; colIndex++) {
	        String title = header[colIndex];
	        XSSFCell cell = titleRow.createCell(colIndex);
	        cell.setCellValue(title);
	        XSSFCellStyle style = workbook.createCellStyle();
	        style.setFont(boldFont);
	        cell.setCellStyle(style);
	    }
	    
	    dumpData(data, sheet);

        for (int j = 0; j < header.length; j++) {
            sheet.autoSizeColumn(j);
        }
	    
	    return workbook;
	}
	
	public static void dumpData(List<Object[]> data, XSSFSheet sheet) throws SQLException {
	    int currentRow = 1;
	    Iterator<Object[]> i = data.iterator();
	    while (i.hasNext()) {
	        Object[] dataRow = i.next();
	        XSSFRow row = sheet.createRow(currentRow++);
	        for (int colIndex = 0; colIndex < dataRow.length; colIndex++) {
	            Object value = dataRow[colIndex];
	            final XSSFCell cell = row.createCell(colIndex);
	            if (value == null) {
	                cell.setCellValue("");
	            } else {
	                if (value instanceof Calendar) {
	                    cell.setCellValue((Calendar) value);
	                } else if (value instanceof Date) {
	                    cell.setCellValue((Date) value);
	                } else if (value instanceof String) {
	                    cell.setCellValue((String) value);
	                } else if (value instanceof Boolean) {
	                    cell.setCellValue((Boolean) value);
	                } else if (value instanceof Double) {
	                    cell.setCellValue((Double) value);
	                } else if (value instanceof BigDecimal) {
	                    cell.setCellValue(((BigDecimal) value).doubleValue());
	                }
	            }
	        }
	    }
	}
	
}
