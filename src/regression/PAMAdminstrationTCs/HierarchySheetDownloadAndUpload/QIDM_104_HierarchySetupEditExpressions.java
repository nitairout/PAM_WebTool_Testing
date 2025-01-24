package regression.PAMAdminstrationTCs.HierarchySheetDownloadAndUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*This test verifies adding and editing expression in 'Edit Expression' sheet, 
 * validate & uploads the sheet and verifies expression are added and edited as expected for measurements 
 * under Manage Measurement page.*/
public class QIDM_104_HierarchySetupEditExpressions extends TestBase {
	LoginTC login = null;
	String fileName = "";	
	String expressionName1 = "[Electricity:ENERGY] [kWh] * 20";
	String expressionName2 = "[Electricity:ENERGY] [kWh] * 2";
	@Test
	public void hierarchySetupEditExpressions() throws Throwable {
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
			
			//Select edit expression checkbox
			getWebElementXpath_D("//label[normalize-space()='Edit Expressions on Locations']/preceding-sibling::input").click();
			//Selecting comodity
			new Select(getWebElementXpath_D("//span[normalize-space()='Commodity:']/following-sibling::div[1]/select")).selectByVisibleText("Electricity");
			//selecting measurement
			new Select(getWebElementXpath_D("//span[normalize-space()='Measurement:']/following-sibling::div[1]/select")).selectByVisibleText("Energy");
			//click to add icon
			getWebElementXpath_D("//i[@class='fa fa-plus fa-fw']").click();

			//Verified "Electricity - Energy" is added on right side of the panel
			Assert.assertEquals(getWebElementXpath_D("//i[@class='fa fa-times']/parent::span").getText(),"Electricity - Energy");
			//Click on 'Generate Configuration Sheet to download the excel' 
			getWebElementXpath_D("//*[@id='btnCreate']/span[normalize-space()='Generate Configuration Sheet']").click();
			Reporter.log("Clicked on 'Generate Configuration Sheet' button to download the excel");
			aJaxWait();
			Thread.sleep(15000);
			
			//Get the latest downloaded file name
			fileName =Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
			Reporter.log("File downloaded successfully:  "+fileName);
			//File name to modify
			String fileToRemoveExpression=Constant.SRC_FOLDER+"\\"+fileName;
			String fileToUpload=Constant.SRC_FOLDER+"\\fileToUpload.xlsx";
			
			//Create a copy of the original file to upload to remove the expression
			FileUtils.copyFile(new File(fileToRemoveExpression), new File(fileToUpload));
			Reporter.log("Create a copy of the file");
			
			FileInputStream fis = new FileInputStream(fileToUpload);
			Reporter.log("Get the latest PAMHierarchyConfig.xlsx file from download folder.");
			Workbook workbook = null;
			Row row =null;
			Sheet sheet = null;
			Cell cell = null;
			//Get all the sheet names.
			
			if (fileToUpload.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileToUpload.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			
			int numberOfSheets = workbook.getNumberOfSheets();

			Iterator<Row> rowIterator =null;
			String cellValue = "";
			int cellType=0;
			for(int i=0; i < numberOfSheets; i++){
				sheet = workbook.getSheetAt(i);
				//Duplicate file modification part starts
				if(sheet.getSheetName().toString().equalsIgnoreCase("Edit Expressions")){
					rowIterator = sheet.iterator();
					int rowNum=0;
					br:
					while (rowIterator.hasNext()) {
						row = rowIterator.next();
						cellValue = "";
						cellType=0;
						rowNum=rowNum+1;
							cell = null;
							if (row.getCell(0) == null) {
								cell = row.createCell(0);
							} else {
								cell = row.getCell(0);
							}

							//cellType = cell.getCellType();
							
							cellValue = "";
							 switch(cell.getCellType()){
							 case STRING:
								 cellValue = cell.getStringCellValue().trim();
								 break;
							 case NUMERIC:
								 cellValue =cell.getNumericCellValue() + "".trim();
								 break;
							 case BOOLEAN:
								 cellValue =cell.getBooleanCellValue() + "".trim();
								 break;
							 case BLANK:
								 cellValue ="";
								 break;
							 case FORMULA:
								 cellValue = cell.getCellFormula() + "".trim();
								 break;
							 default:
								 cellValue = cell.getStringCellValue().trim();
								 break;
							 }
						if(cellValue.equalsIgnoreCase("Automation_TestSite1")){
							cell = row.createCell(3);
							cell.setCellValue(expressionName1);
						}else if (cellValue.equalsIgnoreCase("Automation_TestSite2")) {
							cell = row.createCell(3);
							cell.setCellValue(expressionName2);
						}
					
						if(rowNum==10)
							break br;
					}//row iterator loop
				}//name of the sheet loop
			}//# of sheet loop
			FileOutputStream outputStreamToAdd = new FileOutputStream(fileToUpload);
			workbook.write(outputStreamToAdd);
			outputStreamToAdd.close();
			fis.close();
			Reporter.log("Added the expression into the site");
			
			Utility.moveTheScrollToTheDown();
			
			d.findElement(By.id(locators.getProperty("HierarchyExcelUpload"))).sendKeys(fileToUpload);
			aJaxWait();
			//Verify the file 
			getWebElementActionXpath("HierarchyExcelValid").click();
			aJaxWait();
			//Verify the uploaded message
			verifyText("HierarchyExcelValidationMsg");
			//Click on validate and apply
			getWebElementActionXpath("HierarchyExcelValidApply").click();
			aJaxWait();
			verifyText("HierarchyExcelValidApplyMsg");
			Reporter.log("Uploaded the modified file.");
		
			
			//Navigate to Manage Measurement page
			gotoManageMeasurementPage();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			Reporter.log("Navigate to Manage Measurement page");
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			aJaxWait();
			Thread.sleep(15000);
			
			/*
			 * WebElement element = d.findElement(By.xpath(
			 * "/html/body/form/div[5]/div[1]/div/div[1]/div[1]/div/div[2]/div/se-panel/div/div/div/div/ui-view/div/div[1]/div/div[1]/se-tree-filter/div[1]/input"
			 * )); element.click(); element.clear();
			 * element.sendKeys("Automation_TestSite1"); element.submit();
			 
			//Enter the site name as '"Automation_TestSite1"' in the filter box
			getWebElementActionXpath_D("/html/body/form/div[5]/div[1]/div/div[1]/div[1]/div/div[2]/div/se-panel/div/div/div/div/ui-view/div/div[1]/div/div[1]/se-tree-filter/div[1]/input").click();
			getWebElementActionXpath_D("/html/body/form/div[5]/div[1]/div/div[1]/div[1]/div/div[2]/div/se-panel/div/div/div/div/ui-view/div/div[1]/div/div[1]/se-tree-filter/div[1]/input").clear();
			getWebElementActionXpath_D("/html/body/form/div[5]/div[1]/div/div[1]/div[1]/div/div[2]/div/se-panel/div/div/div/div/ui-view/div/div[1]/div/div[1]/se-tree-filter/div[1]/input").sendKeys("Automation_TestSite1");
			aJaxWait();
			Reporter.log("Clicked on Apply Tags to Locations");
			
			Thread.sleep(5000);
			Reporter.log("Entered the site name as Automation_TestSite1' in the filter box");
			*/
			//click on site
			getWebElementActionXpath_D("//span[contains(text(),'Automation_TestSite1')]").click();
			Thread.sleep(8000);
			
			//Click on electricity commodity 
			//getWebElementActionXpath_D("//commodity-tab/ul/li[2]/span").click();
			getWebElementActionXpath_D("//pam-commodity-tab/ul/li[2]/span").click();
			//search the measurement
			getWebElementActionXpath("FilterByNameAndUOM").click();
			getWebElementActionXpath("FilterByNameAndUOM").sendKeys("Energy");
			
			Assert.assertEquals(getWebElementActionXpath_D("//h4[normalize-space()='Energy']/following-sibling::div//span[1]").getText(), "[Electricity:ENERGY][kWh] * 20");
			Reporter.log("Automation_TestSite1 with expression verification completed");
			
			getWebElementActionXpath_D("//h4[normalize-space()='Energy']/following-sibling::span/i").click();
			Thread.sleep(5000);
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys("[Electricity:ENERGY][kWh] * 2");
			
			getWebElementActionXpath("EditExpression_Validate").click();
			Thread.sleep(5000);
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementActionXpath_D("//h4[normalize-space()='Energy']/following-sibling::div//span[1]").getText(), "[Electricity:ENERGY][kWh] * 2");
			
			//d.navigate().refresh();
			Utility.moveTheScrollToTheTop();
			//click on site
			getWebElementActionXpath_D("//span[contains(text(),'Automation_TestSite2')]").click();
			Thread.sleep(5000);
			
			//search the measurement
			/*getWebElementActionXpath_D("//*[@id='se-calculated-layout']/div/div[2]/div[1]/div/input").click();
			getWebElementActionXpath_D("//*[@id='se-calculated-layout']/div/div[2]/div[1]/div/input").clear();
			getWebElementActionXpath_D("//*[@id='se-calculated-layout']/div/div[2]/div[1]/div/input").sendKeys("Energy");
			*/
			Assert.assertEquals(getWebElementActionXpath_D("//h4[normalize-space()='Energy']/following-sibling::div//span[1]").getText(), "[Electricity:ENERGY][kWh] * 2");
			Reporter.log("Automation_TestSite2 with expression verification completed");
			
			getWebElementActionXpath_D("//h4[normalize-space()='Energy']/following-sibling::span/i").click();
			Thread.sleep(5000);
			
			
			getWebElementXpath("EditExpression_Textarea").click();
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys("1");
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.BACK_SPACE);
			Thread.sleep(500);
			Thread.sleep(5000);
			Utility.moveTheScrollToTheDown();
			//Click on Save & Close button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000);
			
			Thread.sleep(5000);
			
			//Navigate to Hierarchy Setup page
			gotoHierarchySetupPage();
			
			//delete the file if exist for headless chrome
			dir = new File(Constant.DOWNLOAD_PATH);
			for ( File file : dir.listFiles()) {
			    if (file.isFile() && file.getName().contains("PAMHierarchyConfig-") == true) {
			        // If both conditions are true, delete the file
			        file.delete();
			    }
			}
			Utility.moveTheScrollToTheTop();
			//Select edit expression checkbox
			getWebElementXpath_D("//label[normalize-space()='Edit Expressions on Locations']/preceding-sibling::input").click();
			//Selecting comodity
			new Select(getWebElementXpath_D("//span[normalize-space()='Commodity:']/following-sibling::div[1]/select")).selectByVisibleText("Electricity");
			//selecting measurement
			new Select(getWebElementXpath_D("//span[normalize-space()='Measurement:']/following-sibling::div[1]/select")).selectByVisibleText("Energy");
			//click to add icon
			getWebElementXpath_D("//i[@class='fa fa-plus fa-fw']").click();
			Thread.sleep(5000);
			//Verified "Electricity - Energy" is added on right side of the panel
			Assert.assertEquals(getWebElementXpath_D("//i[@class='fa fa-times']/parent::span").getText(),"Electricity - Energy");
			Utility.moveTheScrollToTheTop();
			//Click on 'Generate Configuration Sheet to download the excel' 
			explicitWait_Xpath("//*[@id='btnCreate']/span");
			getWebElementXpath_D("//*[@id='btnCreate']/span").click();
			Reporter.log("Clicked on 'Generate Configuration Sheet' button to download the excel");
			aJaxWait();
			Thread.sleep(15000);
			
			
			//Get the latest downloaded file name
			fileName =Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
			Reporter.log("File downloaded successfully:  "+fileName);
			//File name to modify
			fileToRemoveExpression=Constant.SRC_FOLDER+"\\"+fileName;
			fileToUpload=Constant.SRC_FOLDER+"\\fileToUpload.xlsx";
			
			//Create a copy of the original file to upload to remove the expression
			FileUtils.copyFile(new File(fileToRemoveExpression), new File(fileToUpload));
			Reporter.log("Create a copy of the file");
			
			fis = new FileInputStream(fileToUpload);
			Reporter.log("Get the latest PAMHierarchyConfig.xlsx file from download folder.");
			workbook = null;
			row =null;
			sheet = null;
			cell = null;
			//Get all the sheet names.
			
			if (fileToUpload.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileToUpload.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			
			numberOfSheets = workbook.getNumberOfSheets();

			rowIterator =null;
			cellValue = "";
			cellType=0;
			for(int i=0; i < numberOfSheets; i++){
				sheet = workbook.getSheetAt(i);
				//Duplicate file modification part starts
				if(sheet.getSheetName().toString().equalsIgnoreCase("Edit Expressions")){
					rowIterator = sheet.iterator();
					int rowNum=0;
					br:
					while (rowIterator.hasNext()) {
						row = rowIterator.next();
						cellValue = "";
						cellType=0;
						rowNum=rowNum+1;
							cell = null;
							if (row.getCell(0) == null) {
								cell = row.createCell(0);
							} else {
								cell = row.getCell(0);
							}

							//cellType = cell.getCellType();
							
							cellValue = "";
							 switch(cell.getCellType()){
							 case STRING:
								 cellValue = cell.getStringCellValue().trim();
								 break;
							 case NUMERIC:
								 cellValue =cell.getNumericCellValue() + "".trim();
								 break;
							 case BOOLEAN:
								 cellValue =cell.getBooleanCellValue() + "".trim();
								 break;
							 case BLANK:
								 cellValue ="";
								 break;
							 case FORMULA:
								 cellValue = cell.getCellFormula() + "".trim();
								 break;
							 default:
								 cellValue = cell.getStringCellValue().trim();
								 break;
							 }
						if(cellValue.equalsIgnoreCase("Automation_TestSite1")){
							cell = row.getCell(3);
							Assert.assertEquals(cell.toString(), "[Electricity:ENERGY][kWh] * 2");
						}else if (cellValue.equalsIgnoreCase("Automation_TestSite2")) {
							cell = row.getCell(3);
							Assert.assertNull(cell);
						}
					
						if(rowNum==10)
							break br;
					}//row iterator loop
				}//name of the sheet loop
			}//# of sheet loop

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}


}

