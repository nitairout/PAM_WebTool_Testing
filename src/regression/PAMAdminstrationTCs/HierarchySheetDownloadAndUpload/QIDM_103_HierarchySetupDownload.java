package regression.PAMAdminstrationTCs.HierarchySheetDownloadAndUpload;

import java.io.File;
import java.io.FileInputStream;

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

/*This test verifies hierarchy sheet download and all the expected sheets in the downloaded excel file*/
public class QIDM_103_HierarchySetupDownload  extends TestBase {
	LoginTC login = null;
	String fileName ="";	
	@Test
	public void hierarchySetupDownload() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Hierarchy Setup page
			gotoHierarchySetupPage();
			String clientName=testData.getProperty("ClientName");
			
			//delete the file if exist for headless chrome
			File dir = new File(Constant.DOWNLOAD_PATH);
			for ( File file : dir.listFiles()) {
			    if (file.isFile() && file.getName().contains("PAMHierarchyConfig-") == true) {
			        // If both conditions are true, delete the file
			        file.delete();
			    }
			}
			
			//Click on 'Generate Configuration Sheet' 
			getWebElementXpath_D("//*[@id='btnCreate']/span[normalize-space()='Generate Configuration Sheet']").click();
			printLog("Clicked on 'Generate Configuration Sheet' button to download the excel");
			aJaxWait();
			Thread.sleep(15000);
			//Get the latest downloaded file name
			fileName =Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
			printLog("File downloaded successfully:  "+fileName);
			String originalFileNameToAddNode=Constant.SRC_FOLDER+"\\"+fileName;
			
			//Get all the sheet names.
			String sheetArray[]=testData.getProperty("SetupHierarchySheets").split(",");
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
			//check if the number of sheet is equals to the property file sheet
			Assert.assertTrue(sheetArray.length==numberOfSheets);
			
			for(int i=0; i < numberOfSheets; i++){
				sheet = workbook.getSheetAt(i);
				//check if the sheet name is available
				Assert.assertEquals(sheet.getSheetName(),sheetArray[i]);
				printLog("The sheet name \""+sheetArray[i]+"\" matched successfully");
				//Duplicate file modification part starts
				if(sheet.getSheetName().toString().equalsIgnoreCase("Instructions")){
					row =sheet.getRow(1);
					cell=row.getCell(0);
					Assert.assertTrue(cell.getStringCellValue().contains("Use this file to create and update hierarchies for the Performance Analytics Module. The workflow summary is:"));
					printLog("Verified data from "+sheet.getSheetName().toString());
				}else if(sheet.getSheetName().toString().equalsIgnoreCase("Setup Hierarchy")){
					row =sheet.getRow(4);
					cell=row.getCell(0);
					Assert.assertTrue(cell.getStringCellValue().contains("Automation_TestSite1"));
					printLog("Verified data from "+sheet.getSheetName().toString());
				}else if(sheet.getSheetName().toString().equalsIgnoreCase("Edit Expressions")){
					row =sheet.getRow(2);
					cell=row.getCell(0);
					Assert.assertEquals(cell.getStringCellValue(), "Location Path");
					
					row =sheet.getRow(2);
					cell=row.getCell(1);
					Assert.assertEquals(cell.getStringCellValue(), "Commodity");
					
					row =sheet.getRow(2);
					cell=row.getCell(2);
					Assert.assertEquals(cell.getStringCellValue(), "Measurement Group");
					
					row =sheet.getRow(2);
					cell=row.getCell(3);
					Assert.assertEquals(cell.getStringCellValue(), "Expression (Editable)");
					printLog("Verified data from "+sheet.getSheetName().toString());
				}else if(sheet.getSheetName().toString().equalsIgnoreCase("All Sources (Read-only)")){
					row =sheet.getRow(2);
					cell=row.getCell(0);
					Assert.assertEquals(cell.getStringCellValue(), "Electricity");
					
					row =sheet.getRow(3);
					cell=row.getCell(0);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(1);
					Assert.assertEquals(cell.getStringCellValue(), "Gas");
					
					row =sheet.getRow(3);
					cell=row.getCell(1);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(2);
					Assert.assertEquals(cell.getStringCellValue(), "Water");
					
					row =sheet.getRow(3);
					cell=row.getCell(2);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(3);
					Assert.assertEquals(cell.getStringCellValue(), "Steam");
					
					row =sheet.getRow(3);
					cell=row.getCell(3);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(4);
					Assert.assertEquals(cell.getStringCellValue(), "Air");
					
					row =sheet.getRow(3);
					cell=row.getCell(4);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(5);
					Assert.assertEquals(cell.getStringCellValue(), "Other");
					
					row =sheet.getRow(3);
					cell=row.getCell(5);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(6);
					Assert.assertEquals(cell.getStringCellValue(), "Unknown/Mixed Commodity");
					
					row =sheet.getRow(3);
					cell=row.getCell(6);
					Assert.assertNotNull(cell.getStringCellValue());
					printLog("Verified data from "+sheet.getSheetName().toString());
				}else if(sheet.getSheetName().toString().equalsIgnoreCase("Unmapped Sources (Read-only)")){
					row =sheet.getRow(2);
					cell=row.getCell(0);
					Assert.assertEquals(cell.getStringCellValue(), "Electricity");
					
					row =sheet.getRow(3);
					cell=row.getCell(0);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(1);
					Assert.assertEquals(cell.getStringCellValue(), "Gas");
					
					row =sheet.getRow(3);
					cell=row.getCell(1);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(2);
					Assert.assertEquals(cell.getStringCellValue(), "Water");
					
					row =sheet.getRow(3);
					cell=row.getCell(2);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(3);
					Assert.assertEquals(cell.getStringCellValue(), "Steam");
					
					row =sheet.getRow(3);
					cell=row.getCell(3);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(4);
					Assert.assertEquals(cell.getStringCellValue(), "Air");
					
					row =sheet.getRow(3);
					cell=row.getCell(4);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(5);
					Assert.assertEquals(cell.getStringCellValue(), "Other");
					
					row =sheet.getRow(3);
					cell=row.getCell(5);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(6);
					Assert.assertEquals(cell.getStringCellValue(), "Unknown/Mixed Commodity");
					
					row =sheet.getRow(3);
					cell=row.getCell(6);
					Assert.assertNotNull(cell.getStringCellValue());
					printLog("Verified data from "+sheet.getSheetName().toString());
				}else if(sheet.getSheetName().toString().equalsIgnoreCase("Message Log")){
					row =sheet.getRow(2);
					cell=row.getCell(0);
					Assert.assertEquals(cell.getStringCellValue(), "Date");
					
					row =sheet.getRow(3);
					cell=row.getCell(0);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(1);
					Assert.assertEquals(cell.getStringCellValue(), "Time (Coordinated Universal Time)");
					
					row =sheet.getRow(3);
					cell=row.getCell(1);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(2);
					Assert.assertEquals(cell.getStringCellValue(), "Type");
					
					row =sheet.getRow(2);
					cell=row.getCell(3);
					Assert.assertEquals(cell.getStringCellValue(), "Worksheet");
					
					row =sheet.getRow(3);
					cell=row.getCell(3);
					Assert.assertNotNull(cell.getStringCellValue());
					
					row =sheet.getRow(2);
					cell=row.getCell(4);
					Assert.assertEquals(cell.getStringCellValue(), "Column");
					
					row =sheet.getRow(2);
					cell=row.getCell(5);
					Assert.assertEquals(cell.getStringCellValue(), "Row");
					
					row =sheet.getRow(2);
					cell=row.getCell(6);
					Assert.assertEquals(cell.getStringCellValue(), "Message");
					
					row =sheet.getRow(3);
					cell=row.getCell(6);
					Assert.assertNotNull(cell.getStringCellValue());
					printLog("Verified data from "+sheet.getSheetName().toString());
				}
			}
					
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}


}
