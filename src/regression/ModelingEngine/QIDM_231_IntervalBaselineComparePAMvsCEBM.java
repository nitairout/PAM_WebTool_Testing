package regression.ModelingEngine;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies user defined interval baseline data is matching between PAM and Baseline 
 * tab by comparing the downloaded data files.
 */
public class QIDM_231_IntervalBaselineComparePAMvsCEBM  extends TestBase{
	LoginTC login = null;
	//String baselineName="Interval - Interval";
	@Test
	public void intervalBaselineComparePAMvsCEBM() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Search for site 'PAMTest_Forno 6
			searchSiteInLocationList("PAMTest_Forno 6");
			
			// Selected Energy measurement for site 'PAMTest_Bruciatori forno L6'
			getWebElementXpath("PAMTest_FornoL6_Energy").click();
			
			refreshToLoadTheChart();
			
			//Selected data tab(table)
			getWebElementActionXpath("DataTableTab").click();
			
			//Click on Manage Measurement link from Legend
			Utility.moveTheScrollToTheDown();
			WebElement icon = d.findElement(By.xpath("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[name()='path' and @fill='#2fb5ea']"));
			Actions a = new Actions(d);
			a.moveToElement(icon,1,1).moveToElement(icon,1,1).click().build().perform();
			Thread.sleep(3000);
			
			Thread.sleep(1000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			printLog("Clicked on manage measurement link from PAM page.");
			
			//Baseline tab for create new baseline
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			printLog("Inside baseline tab.");
			
			//clicking edit to generate csv file
			//Removed the verification of baseline here as if the baseline will not present the below line will not execute.
			//Selecting the radio button
			getWebElementXpath_D("//td[.='Interval - Interval']/preceding-sibling::td//child::input").click();
			//click on edit icon
			getWebElementXpath_D("//td[.='Interval - Interval']/following-sibling::td[3]/i").click();
			
			//delete old Baseline download file
			Utility.deleteDownloadFiles("ModelDataset", ".csv");
			//delete old PAM download file
			Utility.deleteDownloadFiles("loadProfileAnalysis", ".csv");
			
			//Download data
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//span[contains(text(),'Download Data')]").click();
			Thread.sleep(6000);
			printLog("Download the file.");
			
			//Get latest Baseline download file
			String latestBaselineDownloadedFile=Utility.latestDownLoadedFileFromDownload("ModelDataset", "csv");

			//Save
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("BaselineSave_button").click();
			Thread.sleep(3000);
			//Save and close
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("Baseline_SaveAndClose").click();
			Thread.sleep(5000);
			printLog("After download the baseline is save and closed.");
			
			//Added Energy & Energy Baseline measurement from Electricity commodity
			Utility.moveTheScrollToTheTop();
			Utility.selectMultipleMeasurements("Electricity#Energy Baseline~standard");
			
			//Change the fixed date range in PAM page
			addFixedDateRange("01/01/2023", "02/01/2023");
			Thread.sleep(10000);// more time to take chart
			// Select "Export Chart Data to CSV" from chart menu options to download "loadProfileAnalysis.csv" file
			kabobMenuOptions("Export Chart Data to CSV");
			Thread.sleep(5000);
			
			//Get latest PAM download file
			String latestPAMDownloadedFile=Utility.latestDownLoadedFileFromDownload("loadProfileAnalysis", "csv");
			
			String pamCSVFile=Constant.DOWNLOAD_PATH+"\\"+latestPAMDownloadedFile;
			String baselineCSVFile=Constant.DOWNLOAD_PATH+"\\"+latestBaselineDownloadedFile;
			
			//Compare the csv, if and only if all the Energy and Actual value are same it will return true else false
			boolean fileCompare=compareCSVWithMonConvert(pamCSVFile,baselineCSVFile);
			Assert.assertTrue(fileCompare);
			printLog("Both the files are same.");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	private boolean compareCSVWithMonConvert(String pamCSVFile,String baselineCSVFile) throws Exception {
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
            int pamIndex=2;
            int cebmIndex=10;
            br:
            for (int i = 0; i < pamDataSize; i++) {
            //for (int i = 2; i < 100; i++) {
                 pamOriginal = pamData.get(pamIndex);
                 baselineOriginal = baselineData.get(cebmIndex);
                 //Getting the value from CSV
                 pamOriginalValue=Double.parseDouble((pamOriginal[1].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[1].replaceAll(",", "")));
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
	                	//System.out.println("CEBM "+baselineOriginal[0]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[0]+" value is "+pamAfterConvert+".");
	                }else {
	                	//System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
	                }
	                
	                pamIndex=pamIndex+1;
		            cebmIndex=cebmIndex+1;
                }else {
                	 pamIndex=pamIndex+1;
                	//To print the missing interval log
                	printLog(pamOriginal[0]+" CEBM interval is missing interval.");
                }
                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
                	break br;
            }            
            printLog("Total CEBM row "+cebmIndex+" and total PAM row "+ pamIndex);
		}catch(Exception e) {
			fileComparison=false;
			throw e;
		}
		return fileComparison;
	}
	
}
