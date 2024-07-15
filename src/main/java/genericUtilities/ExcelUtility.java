package genericUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This class contains reusable methods to read/write data from excel
 * @author sncsr
 *
 */
public class ExcelUtility {
	private Workbook wb;
	private DataFormatter df;

	/**
	 * This method initializes excel
	 * @param excelPath
	 */
	public void excelInit(String excelPath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(excelPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		df = new DataFormatter();
	}
	
	/**
	 * This method fetches data from specified cell
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readFromExcel(String sheetName, int rowNum, int cellNum) {
		return df.formatCellValue(wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum));
	}
	
	/**
	 * This method fetches the data required for specified test case
	 * @param sheetName
	 * @param expectedTestName
	 * @return Map<String, String>
	 */
	public Map<String, String> readFromExcel(String sheetName, String expectedTestName) {
		Map<String, String> map = new HashMap<>();
		Sheet sheet = wb.getSheet(sheetName);
		int requiredRowNum = 0;
		for(int i = 0; i <= sheet.getLastRowNum(); i++) {
			requiredRowNum = i;
			if(df.formatCellValue(sheet.getRow(i).getCell(1)).equalsIgnoreCase(expectedTestName)) 
				break;			
		}
		
		for(int i = requiredRowNum; i <= sheet.getLastRowNum(); i++) {
			if(df.formatCellValue(sheet.getRow(i).getCell(2)).equals("####"))
				break;
			map.put(df.formatCellValue(sheet.getRow(i).getCell(2)), 
					df.formatCellValue(sheet.getRow(i).getCell(3)));
		}
		
		return map;
	}
	
	/**
	 * This method writes to excel
	 * @param sheetName
	 * @param expectedTestName
	 * @param status
	 */
	public void writeToExcel(String sheetName, String expectedTestName, String status) {
		Sheet sheet = wb.getSheet(sheetName);
		for(int i = 0; i <= sheet.getLastRowNum(); i++) {
			if(df.formatCellValue(sheet.getRow(i).getCell(1)).equalsIgnoreCase(expectedTestName)) {
				sheet.getRow(i).createCell(4).setCellValue(status);
				break;			
			}
		}
	}
	
	/**
	 * This method saves the workbook
	 * @param excelPath
	 */
	public void saveExcel(String excelPath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(excelPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method closes the workbook
	 */
	public void closeExcel() {
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
