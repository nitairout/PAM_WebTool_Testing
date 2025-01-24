package regression.ModelingEngine;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;


import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

public class QIDM_241_SiteSchedulePicker extends TestBase{
	LoginTC login = null;
	
	@Test
	public void siteSchedulePicker() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added measurements from commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Search for site name
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			// Click on the measurements
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
			
			//Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(1000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			
			//Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			
			//verify System Generated Baseline should be there
			if(d.findElements(By.xpath("//td[.='System Generated']")).size()<0) {
				throw new Exception("System generaged base line is not present");
			}
			
			//Click on Edit icon of site schedule
			getWebElementXpath_D("//td[.='Site Schedule']/following-sibling::td[3]/i").click();
			Thread.sleep(10000);
			
			//Select 'TOU' from Select a schedule dropdown
			new Select(getWebElementActionXpath_D("//select[@name='scheduleId']")).selectByVisibleText("TOU");
			
			//Click on Generate button
			getWebElementActionXpath("BaselineGenerate_button").click();
			Thread.sleep(10000);
			
			//delete old Baseline download file 'ModelDataset.csv'
			Utility.deleteDownloadFiles("ModelDataset", ".csv");
			
			//Download data
			getWebElementActionXpath_D("//span[contains(text(),'Download Data')]").click();
			Thread.sleep(10000);
			
			//Get latest download file
			String latestDownloadedFile=Utility.latestDownLoadedFileFromDownload("ModelDataset", "csv");
			String actualFile=Constant.DOWNLOAD_PATH+"\\"+latestDownloadedFile;
			
			String expectedFile = null;
			expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_241_SiteSchedule_TOU.csv";
			
			boolean val=compareCSVWithEnergyBaseline(expectedFile,actualFile);
			Assert.assertTrue(val);
			printLog("Both the files are same.");
			
			/*//Select 'Weekday - Weekend' from Select a schedule dropdown
			new Select(getWebElementActionXpath_D("//div[@class='se-input-container']/select[@ng-model='baselineData.scheduleId']")).selectByVisibleText("Weekday - Weekend");
			//Click on Generate button
			getWebElementActionXpath("BaselineGenerate_button").click();
			aJaxWait();
			aJaxWait();
			Thread.sleep(30000);
			
			//delete old Baseline download file 'ModelDataset.csv'
			Utility.deleteDownloadFiles("ModelDataset", ".csv");
			
			//Download data
			getWebElementActionXpath_D("//span[contains(text(),'Download Data')]").click();
			aJaxWait();
			aJaxWait();
			Thread.sleep(30000);
			
			//Get latest download file
			latestDownloadedFile=Utility.latestDownLoadedFileFromDownload("ModelDataset", "csv");
			actualFile=Constant.DOWNLOAD_PATH+"\\"+latestDownloadedFile;
			
			expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_241_SiteSchedule_Weekday_Weekend.csv";
			
			val=compareCSVWithEnergyBaseline(expectedFile,actualFile);
			Assert.assertTrue(val);
			printLog("Both the files are same.");*/
			
			/*//Save
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("BaselineSave_button").click();
			Thread.sleep(3000);
			//Save and close
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("Baseline_SaveAndClose").click();
			Thread.sleep(5000);
			printLog("After download the baseline is save and closed.");*/

			//login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean compareCSVWithEnergyBaseline(String pamCSVFile,String baselineCSVFile) throws Exception {
		boolean fileComparison=true;
		try {
			List<String[]> pamData =Utility.readCSV(pamCSVFile);
            List<String[]> baselineData =Utility.readCSV(baselineCSVFile);
            
            int pamDataSize=pamData.size();
            int baselineDataSize=baselineData.size();
            String[] pamOriginal =null;
            String[] baselineOriginal =null;
            double baselineAfterConvert=0.0;
            double pamAfterConvert=0.0;
            double pamOriginalValue,baselineOriginalValue;
            int pamIndex=10;
            int cebmIndex=10;
            br:
            for (int i = 0; i < pamDataSize; i++) {
                 pamOriginal = pamData.get(pamIndex);
                 baselineOriginal = baselineData.get(cebmIndex);
                 //Getting the value from CSV
                 pamOriginalValue=Double.parseDouble((pamOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[3].replaceAll(",", "")));
                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));

                 //Baseline convert
                baselineAfterConvert=0.0;
               // baselineAfterConvert=Math.round(baselineOriginalValue);
                baselineAfterConvert=baselineOriginalValue;
                
                //PAM convert
                pamAfterConvert=0.0;
                //pamAfterConvert=Math.round(pamOriginalValue);
                pamAfterConvert=pamOriginalValue;
                
                double maxAllowedTharsHold = 1.0;
                double differenceOne = Math.abs(baselineAfterConvert - pamAfterConvert);
                
                //interval missing condition
        		if(baselineOriginal[0].equals(pamOriginal[0])) {
                	//Comparing if the difference between both the value should not be more than 1
                	if(differenceOne > maxAllowedTharsHold) {
	                	fileComparison=false;
	                	printLog("Difference : CEBM "+baselineOriginal[0]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[0]+" value is "+pamAfterConvert+".");
	                	//System.out.println("Baseline "+baselineOriginal[0]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[0]+" value is "+pamAfterConvert+".");
	                }else {
	                	//System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
	                }
	                
	                pamIndex=pamIndex+1;
		            cebmIndex=cebmIndex+1;
                }else {
                	 pamIndex=pamIndex+1;
                	//To print the missing interval log
                	printLog(pamOriginal[0]+" Baseline interval is missing interval.");
                }
                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
                	break br;
            }            
            printLog("Total Baseline row "+cebmIndex+" and total PAM row "+ pamIndex);
		}catch(Exception e) {
			fileComparison=false;
			throw e;
		}
		return fileComparison;
	}

}
