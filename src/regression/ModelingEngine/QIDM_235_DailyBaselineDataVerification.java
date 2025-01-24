package regression.ModelingEngine;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.opencsv.CSVReader;
import common.*;

/*
 * This test verifies user defined daily baseline data is same as expected data file which was saved before to make sure data values are not changed.
 */
public class QIDM_235_DailyBaselineDataVerification extends TestBase{
	LoginTC login = null;
	@Test
	public void dailyBaselineDataVerification() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Gas commodity
			Utility.selectSingleMeasurement("Gas","Energy",standard);

			//Search for site 'PAMTest_Capriata/Saiwa\PAMTest_Main Meter\PAMTest_Gas\PAMTest_Arrivo Gas Metano'
			searchSiteInLocationList("PAMTest_Arrivo Gas Metano");
			// Selected Gas--Energy measurement for site '"PAMTest_Arrivo Gas Metano"'
			getWebElementXpath_D("//tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Arrivo Gas Metano'][1]/child::td[4]/child::span").click();
						
			refreshToLoadTheChart();
			
			/// Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			
			//Verify the Popup title
			String popupTitle = getWebElementActionXpath_D("//div[@class='ra-pam-modal-header']/h3").getText();
			assertEquals(popupTitle, "Configuration of Energy for PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Gas \\ PAMTest_Arrivo Gas Metano");
			
			//Baseline tab for create new baseline
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			getWebElementXpath("CreateNewBaseLine_button").click();
			Thread.sleep(5000);
			
			//Baseline name
			getWebElementXpath("BaselineName").sendKeys("Interval - Daily");
			
			//Start date
			getWebElementXpath("BaselineStarteDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineStarteDate").sendKeys("01/01/2023");
			Thread.sleep(1000);
			
			//Start date
			getWebElementXpath("BaselineEndDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineEndDate").sendKeys("06/30/2023");
			Thread.sleep(1000);
			
			//refresh to update the date
			getWebElementXpath("Baseline_RefreshWhileCreate").click();
			Thread.sleep(8000);
			
			//Select rollup interval;
			new Select(getWebElementActionXpath_D("//select[@name='interval']")).selectByVisibleText("Daily");
			Thread.sleep(10000);
			
			//Generate
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("BaselineGenerate_button").click();
			Thread.sleep(10000);
			
			Utility.moveTheScrollToTheTop();
			//delete old download file
			Utility.deleteDownloadFiles("ModelDataset", "csv");
			
			//Download data
			getWebElementActionXpath_D("//span[contains(text(),'Download Data')]").click();
			Thread.sleep(6000);
			//Get latest download file
			String latestDownloadedFile=Utility.latestDownLoadedFileFromDownload("ModelDataset", "csv");
			
			String expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_235_Expected.csv";
			String actualFile=Constant.DOWNLOAD_PATH+"\\"+latestDownloadedFile;
			
			boolean val=csvComparison(expectedFile,actualFile);
			Assert.assertTrue(val);
			
			Utility.moveTheScrollToTheDown();
			//Close create baseline pop up
			getWebElementActionXpath("Baseline_Close_button").click();
			//Close baseline pop up
			getWebElementActionXpath("BaselineCancel_button").click();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	

    public boolean csvComparison(String expectedfileName,String actualFileName) throws Exception {
        boolean returnVal=false;
        try {
			List<String[]> expectedData =Utility.readCSV(expectedfileName);
            List<String[]> baselineData =Utility.readCSV(actualFileName);
            
            int pamDataSize=expectedData.size();
            int baselineDataSize=baselineData.size();
            String[] pamOriginal =null;
            String[] baselineOriginal =null;
            double baselineAfterConvert=0.0;
            double pamAfterConvert=0.0;
            double pamOriginalValue,baselineOriginalValue;
            int pamIndex=5;
            int cebmIndex=10;
            br:
            for (int i = 0; i < pamDataSize; i++) {
            //for (int i = 2; i < 100; i++) {
                 pamOriginal = expectedData.get(pamIndex);
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
                
                String baselineIntervalAfterMonthConvert=baselineOriginal[0];
                //interval missing condition
                if(pamOriginal[0].equals(baselineIntervalAfterMonthConvert)) {
                	//Comparing if the difference between both the value should not be more than 1
                	if(differenceOne > maxAllowedTharsHold) {
                		returnVal=false;
	                	printLog("Difference : CEBM "+baselineOriginal[0]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[0]+" value is "+pamAfterConvert+".");
	                	System.out.println("CEBM "+baselineOriginal[0]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[0]+" value is "+pamAfterConvert+".");
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
		} catch (Exception e) {
           throw e;
    }
        return returnVal;
    }

    private static List<String[]> readCSV(String filename) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            return reader.readAll();
        }
    }
}