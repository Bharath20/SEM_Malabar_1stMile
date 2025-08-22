package utilPack;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelUtility {
	
	
	String sheetName;

	
	public ExcelUtility(String sheetName) {
		this.sheetName = sheetName;
	}
	
	
	public String ReadData(String header) throws Exception {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"\\Utils\\Clients.xlsx"); 
		//FileInputStream fis = new FileInputStream("C:\\Users\\aswat\\Desktop\\Utils\\Clients.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(0);  //Heading
		int col_num = -1;
		
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(header))
			{	col_num = i;}
		}
		if (col_num == -1) {
			Assert.fail("No heading found in TestData file");
			System.out.println("No heading found in TestData file");
		}

		row = sheet.getRow(1); //Content
		XSSFCell cell = row.getCell(col_num);
		DataFormatter formatter = new DataFormatter();
		String value = formatter.formatCellValue(cell);
	    //System.out.println("Value of the Excel Cell is - " + value);

		return value.trim();

	}

}


	