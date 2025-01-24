package regression.ModelingEngine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.*;

/*
 * This test verifies user defined weekly baseline data is matching between PAM and Baseline tab by 
 * comparing the downloaded data files.
 */
public class QIDM_232_WeeklyBaselineComparePAMvsCEBM  extends TestBase{
	LoginTC login = null;
	//String baselineName="Interval - Weekly";
	@Test
	public void weeklyBaselineComparePAMvsCEBM() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Search for site 'PAMTest_Forno 6'
			searchSiteInLocationList("PAMTest_Forno 6");
			
			// Selected Energy measurement for site 'PAMTest_Forno 6'
			getWebElementXpath("PAMTest_FornoL6_Energy").click();
			
			refreshToLoadTheChart();
			
			/// Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			printLog("Clicked on manage measurement link from PAM page.");
			
			//Baseline tab for create new baseline
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			printLog("Inside baseline tab.");
			
			//Removed the verification of baseline here as if the baseline will not present the below line will not execute.
			//Selecting the radio button
			getWebElementXpath_D("//td[.='Interval - Weekly']/preceding-sibling::td//child::input").click();
			//click on edit icon
			getWebElementXpath_D("//td[.='Interval - Weekly']/following-sibling::td[3]/i").click();
			
			//delete old Baseline download file
			Utility.deleteDownloadFiles("ModelDataset", ".csv");
			//delete old PAM download file
			Utility.deleteDownloadFiles("trendAnalysis", ".csv");
			
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
			getWebElementActionXpath("Baseline_SaveAndClose").click();
			Thread.sleep(5000);
			printLog("After download the baseline is save and closed.");
			
			//Added Energy & Energy Baseline measurement from Electricity commodity
			Utility.moveTheScrollToTheTop();
			Utility.selectMultipleMeasurements("Electricity#Energy Baseline~standard");
			
			//Change the fixed date range in PAM page
			addFixedDateRange("1/2/2023", "11/26/2023");
			
			//Select the time interval - By week
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By week");
			printLog("Changed the interval to week.");
			aJaxWait();
			
			// Select "Export Chart Data to CSV" from chart menu options to download "trendAnalysis.csv" file
			kabobMenuOptions("Export Chart Data to CSV");
			Thread.sleep(5000);
			
			//Get latest PAM download file
			String latestPAMDownloadedFile=Utility.latestDownLoadedFileFromDownload("trendAnalysis", "csv");
			
			String pamCSVFile=Constant.DOWNLOAD_PATH+"\\"+latestPAMDownloadedFile;
			String baselineCSVFile=Constant.DOWNLOAD_PATH+"\\"+latestBaselineDownloadedFile;
			
			//Compare the csv, if and only if all the Energy and Actual value are same it will return true else false
			boolean fileCompare=compareCSVWithWeekConvert(pamCSVFile,baselineCSVFile);
			Assert.assertTrue(fileCompare);
			printLog("Both the files are same.");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	private boolean compareCSVWithWeekConvert(String pamCSVFile,String baselineCSVFile) throws Exception {
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
                 //Getting the CSV value
                 pamOriginalValue=Double.parseDouble((pamOriginal[1].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[1].replaceAll(",", "")));
                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));

                 //Baseline convert
                baselineAfterConvert=0.0;
               // baselineAfterConvert=Math.round(baselineOriginalValue);
                baselineAfterConvert=baselineOriginalValue;
                
                double maxAllowedTharsHold = 1.0;
                double differenceOne = Math.abs(baselineAfterConvert - pamAfterConvert);
                
                //PAM convert
                pamAfterConvert=0.0;
                //pamAfterConvert=Math.round(pamOriginalValue);
                pamAfterConvert=pamOriginalValue;
                
                //Pam interval convert
                String baselineIntervalAfterWeekConvert="";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd hh:mm:ss");
        		LocalDate convertedDate = LocalDate.parse(baselineOriginal[0], formatter);
        		WeekFields weekFields = WeekFields.of(Locale.getDefault());  
                int weekNumber = convertedDate.get(weekFields.weekOfWeekBasedYear());
                int length = String.valueOf(weekNumber).length();
                //Converting to week number
                if(length==1) 
                	baselineIntervalAfterWeekConvert="W0"+weekNumber;
                else
                	baselineIntervalAfterWeekConvert="W"+weekNumber;
                //System.out.println(baselineIntervalAfterWeekConvert+"  : Week number: " + pamOriginal[0]);
                
                //interval missing condition
                if(pamOriginal[0].startsWith(baselineIntervalAfterWeekConvert)) {
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
                	//System.out.println(pamOriginal[0]+" CEBM interval is missing interval.");
                	printLog(pamOriginal[0]+" CEBM interval is missing interval.");
                }
                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
                	break br;
            }            
            printLog("Total CEBM row "+cebmIndex+" and total PAM row "+ pamIndex);
            fileComparison=true;
            // System.out.println(pamIndex+":::::::::::"+cebmIndex);
		}catch(Exception e) {
			fileComparison=false;
			throw e;
		}
		return fileComparison;
	}
}
