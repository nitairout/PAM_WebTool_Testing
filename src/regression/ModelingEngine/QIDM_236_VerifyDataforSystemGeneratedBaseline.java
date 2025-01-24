package regression.ModelingEngine;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies vaues for system generated baseline
 * is as expected before by comparing current values with expected data file saved before.
 */
public class QIDM_236_VerifyDataforSystemGeneratedBaseline extends TestBase{
	LoginTC login = null;
	String defaultBaseline = "System Generated";
	String PAMTest_Bruciatori_forno3_measurementPath = "//tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Bruciatori forno 3'][1]/child::td[3]/child::span";
	
	@Test
	public void verifyDataforSystemGeneratedBaseline() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Provide site name, measurements under commodity and measurement icon path
			verifySystemGeneratedBaseLineForCommodity("PAMTest_Bruciatori forno 3","Electricity","Energy",PAMTest_Bruciatori_forno3_measurementPath);						

			//login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void verifySystemGeneratedBaseLineForCommodity(String sitename1, String commodity, String measurement, String measurementIcon) throws Throwable {
		try {
			//Added measurements from commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Search for site name
			searchSiteInLocationList(sitename1);
			getWebElementXpath_D(measurementIcon).click();
			
			refreshToLoadTheChart();
			
			//Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
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
			getWebElementXpath_D("//td[.='"+defaultBaseline+"']/following-sibling::td[3]/i").click();
			Thread.sleep(5000);
			
			//delete old Baseline download file
			Utility.deleteDownloadFiles("ModelDataset", "csv");
			//delete old PAM download file
			Utility.deleteDownloadFiles("trendAnalysis", ".csv");
			
			//Download data
			getWebElementActionXpath_D("//span[contains(text(),'Download Data')]").click();
			Thread.sleep(6000);
			//Get latest download file
			String latestDownloadedFile=Utility.latestDownLoadedFileFromDownload("ModelDataset", "csv");
			String actualFile=Constant.DOWNLOAD_PATH+"\\"+latestDownloadedFile;
			
			String expectedFile = null;
			if("https://tk1.dev.summitenergy.com".contains(Dto.getURl())) {
				expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_236_SystemGeneratedTK1.csv";
			}
			else if("https://core.stg1.resourceadvisor.schneider-electric.com/login.aspx".contains(Dto.getURl())) {
				expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_236_SystemGeneratedStage.csv";
			}
			else if("https://resourceadvisor.schneider-electric.com".contains(Dto.getURl())) {
				expectedFile=System.getProperty("user.dir")+ "\\src\\config\\ModelingTestData\\QIDM_236_SystemGeneratedProd.csv";
			}
			boolean val=csvComparison(expectedFile,actualFile);
			Assert.assertTrue(val);
			printLog("Both the files are same.");
			
			Utility.moveTheScrollToTheDown();
			
			//Close baseline pop up
			getWebElementActionXpath("BaselineCancel_button").click();
			
			//Close create baseline pop up
			getWebElementActionXpath("Baseline_Close_button").click();
			
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
            System.out.println("PAM Data size is: "+pamDataSize);
            int baselineDataSize=baselineData.size();
            System.out.println("Baseline Data size is: "+baselineDataSize);
            String[] pamOriginal =null;
            String[] baselineOriginal =null;
            double baselineAfterConvert=0.0;
            double pamAfterConvert=0.0;
            double pamOriginalValue,baselineOriginalValue;
            int pamIndex=5;
            int cebmIndex=10;
            br:
            for (int i = 0; i < (pamDataSize-5); i++) {
            //for (int i = 2; i < 100; i++) {
                 pamOriginal = expectedData.get(pamIndex);
                 baselineOriginal = baselineData.get(cebmIndex);
                 //Getting the value from CSV
                 pamOriginalValue=Double.parseDouble((pamOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[3].replaceAll(",", "")));
                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));

                 //Baseline convert
                baselineAfterConvert=0.0;
                baselineAfterConvert=Math.round(baselineOriginalValue);
                baselineAfterConvert=baselineOriginalValue;
                
                //PAM convert
                pamAfterConvert=0.0;
                pamAfterConvert=Math.round(pamOriginalValue);
                pamAfterConvert=pamOriginalValue;
                
                double maxAllowedTharsHold = 1.0;
                double differenceOne = Math.abs(baselineAfterConvert - pamAfterConvert);
               // 467.3069467779157
               // 467.3083690267435
                String baselineIntervalAfterMonthConvert=baselineOriginal[0];
                //interval missing condition
                if(pamOriginal[0].equals(baselineIntervalAfterMonthConvert)) {
                	//Comparing if the difference between both the value should not be more than 1
                	if(differenceOne > maxAllowedTharsHold) {
                		returnVal=false;
	                	printLog("Difference : CEBM "+baselineOriginal[3]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[3]+" value is "+pamAfterConvert+".");
	                	System.out.println("CEBM "+baselineOriginal[3]+" value is "+baselineAfterConvert+", PAM "+pamOriginal[3]+" value is "+pamAfterConvert+".");
	                }else {
	                	returnVal=true;
	                	System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
	                }
	                
	                pamIndex=pamIndex+1;
		            cebmIndex=cebmIndex+1;
                }else {
                	 pamIndex=pamIndex+1;
                	//To print the missing interval log
                	printLog(pamOriginal[3]+" CEBM interval is missing interval.");
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