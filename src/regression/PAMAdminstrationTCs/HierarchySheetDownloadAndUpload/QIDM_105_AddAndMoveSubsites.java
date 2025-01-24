package regression.PAMAdminstrationTCs.HierarchySheetDownloadAndUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * As a client manager I am able to download a list of my client's interval data sources 
 * So I can update the configuration more quickly and inexpensively.
 * */

public class QIDM_105_AddAndMoveSubsites  extends TestBase {
	LoginTC login = null;
	String fileName ="";	
	String movingSite2="Auto_SubSite2";
	String movingSite1="Auto_SubSite1";
	String clientName="";
	String fileTOUpload="";
	File dir =null;
	//Declared this value to upload again in finally block if the orginal file upload got failed
	boolean checkFileUpload=true;
	@Test
	public void addAndMoveSubsites() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Hierarchy Setup page
			gotoHierarchySetupPage();
			clientName=testData.getProperty("ClientName");
			
			//delete the file if exist for headless chrome
			File dir = new File(Constant.DOWNLOAD_PATH);
			for ( File file : dir.listFiles()) {
			    if (file.isFile() && file.getName().contains("PAMHierarchyConfig-") == true) {
			        // If both conditions are true, delete the file
			        file.delete();
			    }
			}
			
			//Click on 'Generate Configuration Sheet' 
			getWebElementXpath_D("//*[@id='btnCreate']/span").click();
			printLog("Clicked on 'Generate Configuration Sheet' button to download the excel");
			Thread.sleep(18000);
			
			//Get the latest downloaded file name
			fileName =Constant.SRC_FOLDER+"\\"+Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
			printLog("File downloaded successfully:  "+fileName);

			//Return the updated file where the sub sites are moved
			fileTOUpload=fileModification(fileName,"Automation_TestSite1","Automation_TestSite2",0);

			//Updated file uploading part starts
			getWebElementActionID("HierarchyExcelUpload").sendKeys(fileTOUpload);
			Thread.sleep(5000);
			//Verify the file 
			getWebElementActionXpath("HierarchyExcelValid").click();
			Thread.sleep(15000);
			//Verify the uploaded message
			verifyText("HierarchyExcelValidationMsg");
			//Click on validate and apply
			getWebElementActionXpath("HierarchyExcelValidApply").click();
			aJaxWait();
			verifyText("HierarchyExcelValidApplyMsg");
			Reporter.log("Uploaded the modified file.");
			
			//Going to PAMpage to verify if the subsite moved is successfully applied
			goToAnalysisPage();
			Utility.moveTheScrollToTheTop();
			goToPAMCard("TrendAnalysisCard");
			
			//Search and expand Automation_TestSite2 to verify Auto_SubSite2 is present
			searchSiteInLocationList("Automation_TestSite2");
			getWebElementXpath_D("//td[normalize-space()='Automation_TestSite2']//*[contains(@class,'k-svg-icon')]").click();
			Assert.assertEquals("Auto_SubSite2", getWebElementXpath_D("//td[normalize-space()='Auto_SubSite2']").getText());
			printLog("Verified the subsite is present under Automation_TestSite2");
			
			//Search and expand Automation_TestSite1 to verify Auto_SubSite2 is not present
			searchSiteInLocationList("Automation_TestSite1");
			getWebElementXpath_D("//td[normalize-space()='Automation_TestSite1']//*[contains(@class,'k-svg-icon')]").click();
			Assert.assertTrue((d.findElements(By.xpath("//td[normalize-space()='Auto_SubSite2']")).size()==0));
			printLog("Verified the subsite is not present under Automation_TestSite1");
			
			//Navigate to Hierarchy Setup page to download modify to original and upload the file
			gotoHierarchySetupPage();
			checkFileUpload=true;
			
			dir = new File(Constant.DOWNLOAD_PATH);
			for ( File file : dir.listFiles()) {
			    if (file.isFile() && file.getName().contains("PAMHierarchyConfig-") == true) {
			        // If both conditions are true, delete the file
			        file.delete();
			    }
			}
			
			//Click on 'Generate Configuration Sheet' 
			getWebElementXpath_D("//*[@id='btnCreate']/span").click();
			printLog("Clicked on 'Generate Configuration Sheet' button to download the excel");
			Thread.sleep(18000);
			//Get the latest downloaded file name
			fileName =Constant.SRC_FOLDER+"\\"+Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
			printLog("File downloaded successfully:  "+fileName);
			
			//Return the updated file which is same as original file
			fileTOUpload=fileModification(fileName,"Automation_TestSite1","Automation_TestSite2",1);
			
			//Uploaded the orginal file
			getWebElementActionID("HierarchyExcelUpload").sendKeys(fileTOUpload);
			Thread.sleep(2000);
						
			//Verify the file 
			getWebElementActionXpath("HierarchyExcelValid").click();
			Thread.sleep(15000);
			//Verify the uploaded message
			verifyText("HierarchyExcelValidationMsg");
			// validate and apply
			getWebElementActionXpath("HierarchyExcelValidApply").click();
			aJaxWait();
			verifyText("HierarchyExcelValidApplyMsg");
			Reporter.log("Uploaded the modified file.");
			checkFileUpload=false;
			
			//Going to PAMpage to verify if the subsite removed is successfully applied
			goToAnalysisPage();
			Utility.moveTheScrollToTheTop();
			goToPAMCard("TrendAnalysisCard");
			
			//Search and verify Auto_SubSite2 is not present under Automation_TestSite2
			d.navigate().refresh();
			Thread.sleep(8000);
			searchSiteInLocationList("Auto_SubSite2");
			Assert.assertEquals(1, d.findElements(By.xpath("//td[normalize-space()='Auto_SubSite2']")).size());
			Assert.assertEquals(1, d.findElements(By.xpath("//td[normalize-space()='Automation_TestSite1']")).size());
			Assert.assertEquals(0, d.findElements(By.xpath("//td[normalize-space()='Automation_TestSite2']")).size());
			printLog("Verified the subsite is present under Automation_TestSite1");
			
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
			if(checkFileUpload) {
				gotoHierarchySetupPage();
				dir = new File(Constant.DOWNLOAD_PATH);
				for ( File file : dir.listFiles()) {
				    if (file.isFile() && file.getName().contains("PAMHierarchyConfig-") == true) {
				        // If both conditions are true, delete the file
				        file.delete();
				    }
				}
				
				//Click on 'Generate Configuration Sheet' 
				getWebElementXpath_D("//*[@id='btnCreate']/span").click();
				printLog("Clicked on 'Generate Configuration Sheet' button to download the excel");
				Thread.sleep(18000);
				//Get the latest downloaded file name
				fileName =Constant.SRC_FOLDER+"\\"+Utility.latestDownLoadedFileFromDownload("PAMHierarchyConfig-"+clientName, "Download.xlsx");
				//Uploaded the orginal file
				fileTOUpload=fileModification(fileName,"Automation_TestSite1","Automation_TestSite2",1);
				getWebElementID("HierarchyExcelUpload").sendKeys(fileTOUpload);
				Thread.sleep(2000);
				//Verify the file 
				getWebElementActionXpath("HierarchyExcelValid").click();
				Thread.sleep(15000);
				// validate and apply
				getWebElementActionXpath("HierarchyExcelValidApply").click();
				Thread.sleep(15000);
			}
			login.logout();
			}catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	private String  fileModification(String updatingFileName,String Automation_TestSite1,String Automation_TestSite2,int updatedType) throws Exception {
		try {
			int siteMoveCounter1=0;
			int siteMoveCounter2=0;
			int addSiteCount1=0;
			int addSiteCount2=0;
			
			/*Used to update the excel those many time. If 0 then excel will update Automation_TestSite2-->Auto_SubSite2  Automation_TestSite1-->Auto_SubSite1 if 1 then Automation_TestSite1-->Auto_SubSite2
			If 1 then the loop will run 3 times as Automation_TestSite2 should not update Auto_SubSite1/Auto_SubSite2
			*/
			int theNumberOfTimeimeXcelUpdate=0;
				if(updatedType==0) {
					theNumberOfTimeimeXcelUpdate=4;
				}
				else if(updatedType==1) {
					theNumberOfTimeimeXcelUpdate=3;
				}
			
			//Based on loop the # of time the excel will update, 0 and 1 will create blank row. 2 and 3 will update sub site.
			for(int updateVal=0;updateVal<theNumberOfTimeimeXcelUpdate;updateVal++){
				FileInputStream fis = new FileInputStream(updatingFileName);
				Reporter.log("Get the latest PAMHierarchyConfig.xlsx file from download folder.");
				Workbook workbook = null;
				Row row =null;
				Sheet sheet = null;
				Cell cell = null;
				//Get all the sheet names.
				
				if (updatingFileName.toLowerCase().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (updatingFileName.toLowerCase().endsWith("xls")) {
					workbook = new HSSFWorkbook(fis);
				}
				
				int numberOfSheets = workbook.getNumberOfSheets();
				Iterator<Row> rowIterator =null;
				int cellType=0;
				int maxNumOfCells = 0;
				String cellValue = "";
				//If value is 0 then delete the entire row
				//if value is 1 then create  new row and add Auto_SubSite2
				br:
				for(int i=0; i < numberOfSheets; i++){
					sheet = workbook.getSheetAt(i);
					//check if the sheet name is available
					//Duplicate file modification part starts
					if(sheet.getSheetName().toString().equalsIgnoreCase("Setup Hierarchy")){
							rowIterator = sheet.iterator();
							while (rowIterator.hasNext()) {
									row = rowIterator.next();
									//maxNumOfCells = row.getLastCellNum();
									maxNumOfCells = 5;
									cellValue = "";
									cellType=0;
									for (int cellCounter = 0; cellCounter < maxNumOfCells; cellCounter++) {
										cell = null;
										if (row.getCell(cellCounter) == null) {
											cell = row.createCell(cellCounter);
										} else {
											cell = row.getCell(cellCounter);
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
										 if(updatedType==0) {
											if(updateVal==2 || updateVal==3){
												if(Automation_TestSite1.equalsIgnoreCase(cellValue) && addSiteCount1==0) {
														int rowNum=row.getRowNum()+1;
														sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1);
														Row newRow = sheet.createRow(rowNum);
														cell=newRow.createCell(1);
											        	cell.setCellValue(movingSite1);
											        	addSiteCount1++;
											        	break br;
												}else if(Automation_TestSite2.equalsIgnoreCase(cellValue) && addSiteCount2==0) {
													int rowNum=row.getRowNum()+1;
													sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1);
													Row newRow = sheet.createRow(rowNum);
													cell=newRow.createCell(1);
										        	cell.setCellValue(movingSite2);
										        	addSiteCount2++;
										        	break br;
												}
												
													
											}
											if(updateVal==0 || updateVal==1){
												if(movingSite1.equalsIgnoreCase(cellValue)  && siteMoveCounter1==0) {
													sheet.shiftRows(row.getRowNum()+1, sheet.getLastRowNum(), -1);
													siteMoveCounter1++;
													break br;
												}else if(movingSite2.equalsIgnoreCase(cellValue) && siteMoveCounter2==0) {
													sheet.shiftRows(row.getRowNum()+1, sheet.getLastRowNum(), -1);
													siteMoveCounter2++;
													break br;
												}
											}
										 }else  if(updatedType==1) {
												if(updateVal==2){
													if(Automation_TestSite1.equalsIgnoreCase(cellValue) && addSiteCount1==0) {
															int rowNum=row.getRowNum()+1;
															sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1);
															Row newRow = sheet.createRow(rowNum);
															cell=newRow.createCell(1);
												        	cell.setCellValue(movingSite2);
												        	addSiteCount1++;
												        	break br;
													}
												}
												if(updateVal==0 || updateVal==1){
													if(movingSite1.equalsIgnoreCase(cellValue)  && siteMoveCounter1==0) {
														sheet.shiftRows(row.getRowNum()+1, sheet.getLastRowNum(), -1);
														siteMoveCounter1++;
														break br;
													}else if(movingSite2.equalsIgnoreCase(cellValue) && siteMoveCounter2==0) {
														sheet.shiftRows(row.getRowNum()+1, sheet.getLastRowNum(), -1);
														siteMoveCounter2++;
														break br;
													}
												}
											 }
									}//cell
								}//row iterator
						}//sheet name
				}//sheet
				FileOutputStream outputStreamToAdd = new FileOutputStream(updatingFileName);
				workbook.write(outputStreamToAdd);
				workbook.close();
				outputStreamToAdd.close();
				fis.close();
			}
			return updatingFileName;
		}catch(Exception e) {
			throw e;
		}
	}
	

}
