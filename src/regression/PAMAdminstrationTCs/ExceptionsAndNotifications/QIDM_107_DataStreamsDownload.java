package regression.PAMAdminstrationTCs.ExceptionsAndNotifications;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * The validation message log can aid in identifying issues with my changes on hierachy setup before attempting to apply them.
 */
public class QIDM_107_DataStreamsDownload extends TestBase {
	LoginTC login = null;
	String fileName ="";
	String dataStreamExcelColumnHeader = "Location Path,Location ID,Commodity,Measurement Group,Measurement Group ID,Type,EEM Source Node,EEM Source ID,EEM Measurement,EEM Measurement ID,Expression,Timezone,Interval (min),Status,Location Tags";
	//String dataStreamExcelFirstRowValues = "Automation_TestSite1,103145,Weather,TEMPERATURE,18,Metered,KLHZ - LOUISBURG,4211,Wthr Temp (F),9005,,Eastern Standard Time,60,Online, ";
	@Test
	public void dataStreamsDownload() throws Throwable {
		try {
			//delete the file if exist the file name as 'DataStreams.xlsx'
			Utility.deleteDownloadFiles("DataStreams", ".xlsx");

			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Data Streams Tab page under Administration menu tab
			gotoDataStreamsPage();
			
			//Click on 'Excel Download' button
			//getWebElementActionXpath_D("//a[@class='buttonMain']").click();
			getWebElementActionXpath_D("//button[@type='button']").click();
			aJaxWait();
			Thread.sleep(10000);
			
			//Get the latest downloaded file name
			fileName =Utility.latestDownLoadedFileFromDownload("DataStreams", ".xlsx");
			printLog("File downloaded successfully:  "+fileName);
			
			String originalFileNameToAddNode=Constant.SRC_FOLDER+"\\"+fileName;

			//Get all the sheet names, it should be 1 sheet
			FileInputStream fis = new FileInputStream(originalFileNameToAddNode);
			Workbook workbook = null;
			Row row =null;
			Sheet sheet = null;
			Cell cell = null;
			if (originalFileNameToAddNode.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (originalFileNameToAddNode.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			
			int numberOfSheets = workbook.getNumberOfSheets();
			//check if the number of sheet is 1
			Assert.assertTrue(numberOfSheets == 1);
			//Verified the only Column Headers of the sheet, ad data values may differ in all the 3 environments.
			List<String> columnHeaders=Arrays.asList(dataStreamExcelColumnHeader.split(","));
			//List<String> dataStreamValues=Arrays.asList(dataStreamExcelFirstRowValues.split(","));
			sheet = workbook.getSheetAt(0);
			//Verified both Headers and Data
			for(int i=0;i<columnHeaders.size();i++) {
				row =sheet.getRow(0);
				cell=row.getCell(i);
				Assert.assertEquals(cell.getStringCellValue().trim(),columnHeaders.get(i));
				
				//row =sheet.getRow(1);
				//cell=row.getCell(i);
				//Assert.assertEquals(cell.getStringCellValue().trim(),dataStreamValues.get(i).trim());
			}
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}