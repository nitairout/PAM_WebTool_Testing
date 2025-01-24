package regression.LoadProfileAnalysisCard;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
 * This test validates the Export functionality including - Export Source Data, 
 * Export Chart Data to CSV and Export to CSV of the Load Profile analysis card 
 * and comparing the values against the Data Table
 */
public class QIDM_49_LoadProfileExportOptions extends TestBase{
	LoginTC login = null;
	String excelColumnHeader = "Date/Time,Date/Time,PAMTest_Sala tecnica cts,Status,Note,PAMTest_Sala tecnica cts,Status,Note";	
	String excelFirstRowValues = "2023-01-01 00:15:00,2022-12-31 23:15:00,2.089,Valid,,708453.54,Valid,";
	
	@Test
	public void loadProfileExportOptions() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Utility.selectMultipleMeasurements("Electricity*Energy|Electricity*Energy Cumulative");
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*Energy Cumulative~standard");
			//Searched with site 'PAMTest_Sala tecnica cts'
			searchSiteInLocationList("PAMTest_Sala tecnica cts");
			//Click on the Electricity Measurement
			getWebElementXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Sala tecnica cts kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Sala tecnica cts kWh");
			printLog("Legend verified for 2 measurements are completed.");
			
			//delete all files based on name so that we will get fresh file everytime
			Utility.deleteDownloadFiles("loadProfileAnalysis",".csv");
			Utility.deleteDownloadFiles("LoadProfile",".xlsx");
			
			//Select "Export Chart Data to CSV" from kabob menu options
			kabobMenuOptions("Export Chart Data to CSV");
			
			// Get the data from CSV file
			ArrayList<List<String>> csvDataToCheckTheMsg = Utility.returnNumberOfRowsFromcsv("loadProfileAnalysis", 6);
			List<String> firstRowData = null;
			firstRowData = csvDataToCheckTheMsg.get(1);
			Assert.assertEquals(firstRowData.get(1),testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(firstRowData.get(2),"Energy Cumulative (kWh)");
			printLog("Verified the measuremnts available on downloaded csv file");			
			
			// Click on KabobMenu and select 'Export Source Data'.
			kabobMenuOptions("Export Source Data");
			
			//click on Format tab check if the excel radio button is checked else check it
			getWebElementXpath_D("//mat-dialog-container[contains(@id,'mat-mdc-dialog')]//ul[@role='tablist']/li[2]").click();
			if(!getWebElementXpath_D("//label[normalize-space()='Excel (.xlsx)']/preceding-sibling::input").isSelected()) {
				getWebElementXpath_D("//label[normalize-space()='Excel (.xlsx)']/preceding-sibling::input").click();
			}
			
			//Data quality : Show data quality falg should be checked else check it
			getWebElementXpath_D("//mat-dialog-container[contains(@id,'mat-mdc-dialog')]//ul[@role='tablist']/li[3]").click();
			if(!getWebElementXpath_D("//label[normalize-space()='Show Data Quality Flags']/preceding-sibling::input").isSelected()) {
				getWebElementXpath_D("//label[normalize-space()='Show Data Quality Flags']/preceding-sibling::input").click();
			}
			
			//Data quality : Show Source IDs should be checked else check it
			if(!getWebElementXpath_D("//label[normalize-space()='Show Source IDs']/preceding-sibling::input").isSelected()) {
				getWebElementXpath_D("//label[normalize-space()='Show Source IDs']/preceding-sibling::input").click();
			}
			
			//Data quality : Show cumulative data stream (if available) should be checked else check it
			if(!getWebElementXpath_D("//label[normalize-space()='Show cumulative data stream (if available)']/preceding-sibling::input").isSelected()) {
				getWebElementXpath_D("//label[normalize-space()='Show cumulative data stream (if available)']/preceding-sibling::input").click();
			}
			
			//Click on Export button
			getWebElementXpath_D("//button[normalize-space()='Export']").click();
			Thread.sleep(5000);
			
			//Get the latest downloaded file name
			String fileName =Utility.latestDownLoadedFileFromDownload("LoadProfile", ".xlsx");
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
			//Verified the Column Headers of the sheet
			List<String> columnHeaders=Arrays.asList(excelColumnHeader.split(","));
			List<String> dataValues=Arrays.asList(excelFirstRowValues.split(",", -1));
			sheet = workbook.getSheetAt(0);
			//Verified both Headers and Data
			DataFormatter formatter = new DataFormatter();
			String val = "";
			for(int i=0;i<columnHeaders.size();i++) {
				row =sheet.getRow(0);
				cell=row.getCell(i);
				Assert.assertEquals(cell.getStringCellValue().trim(),columnHeaders.get(i));
				
				row =sheet.getRow(3);
				cell=row.getCell(i);
				val = formatter.formatCellValue(cell);
				Assert.assertEquals(val.trim(),dataValues.get(i).trim());
			}
			printLog("Verified the headers and data on downloaded xlsx file");
			
			//delete all files based on name so that we will get fresh file everytime
			Utility.deleteDownloadFiles("trendAnalysis",".pdf");
			// Click on KabobMenu and select 'Export to PDF'.
			kabobMenuOptions("Export to PDF");			
			fileName =Utility.latestDownLoadedFileFromDownload("loadProfileAnalysis", "pdf");
			File fileLenth=new File(Constant.DOWNLOAD_PATH+"/"+fileName);
			if(fileLenth.length()<=0){
				throw new Exception("check if the file is not downloaded of the file doesnot contain anything");
			}
			printLog("Successfully downloaded PDF file");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}