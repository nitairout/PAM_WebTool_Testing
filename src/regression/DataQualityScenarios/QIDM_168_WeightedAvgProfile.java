package regression.DataQualityScenarios;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies data for a site where we apply weighted average rule to fill the gaps in corrected mode.
 * */
public class QIDM_168_WeightedAvgProfile extends TestBase {
	LoginTC login = null;
	@Test
	public void weightedAvgProfile() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			// Navigate to LoadProfileAnalysisCard
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Select Electric commodity for a site WeightedAvgProfile
			searchSiteInLocationList("WeightedAvgProfile");
			getWebElementXpath("DQElectricitySiteAfterFilter").click();
			Reporter.log("Searched for 'WeightedAvgProfile' site");

			// Select the Fixed date range of June262019 and June262019
			addFixedDateRange("06/26/2019","06/26/2019");
			
			//Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Data quality flag on
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");
			
			//Load the chart
			refreshToLoadTheChart();
			
			//Include all data points radio button from chart option
			includeAllDataPointsOnAxesTabChartOption();

			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ WeightedAvgProfile kWh");
			printLog("Legend verified for Raw mode.");
			
			//Table data verification started
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ WeightedAvgProfile");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for Raw mode.");
			
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[20]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[35]");
			Thread.sleep(2000);
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			String rawModeTableData[]="6/26/2019 09:30 AM~~Unknown|6/26/2019 09:45 AM~~Unknown|6/26/2019 10:00 AM~~Unknown|6/26/2019 10:15 AM~~Unknown|6/26/2019 10:30 AM~~Unknown|6/26/2019 10:45 AM~~Unknown|6/26/2019 11:00 AM~6,447~Suspect+Note".split("\\|");
			
			
			int j=19;
			int movingTill=25;
			int rowVal=j;
			
			String rawModeRowWiseData[]=null;
			for(;j<=movingTill;j++) {
				rawModeRowWiseData=rawModeTableData[(j-rowVal)].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+j+"]/th").getText(), rawModeRowWiseData[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+j+"]/td[1]").getText(), rawModeRowWiseData[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+j+"]/td[2]").getText(), rawModeRowWiseData[2]);
			}
			printLog("Raw mode functionality complated!!");
			
			//Enlarge the bottom panel 
			hideEenlargeBottomTabsPanel();
			
			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			Thread.sleep(6000);
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ WeightedAvgProfile kWh");
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification 
			WebElement icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			String iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 7 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			//Check the number of flags present in chart
			int flagSize=getSizeOfElement(locators.getProperty("DataQualityFlagsOnCorectMode"),"xpath");
			Assert.assertEquals(flagSize, 7);
			
			//Verify the data into pop up. clicking on 7th flag
			getWebElementActionXpath_D("(//*[contains(@class,'highcharts-point')])[7]").click();
			Thread.sleep(4000);
			Assert.assertEquals(getWebElementActionXpath("ChartFlagPopUpHeaderSiteName").getText(), "QA Site 1 \\...\\ WeightedAvgProfile");
			
			//Verified 1st note i.e. 1st click (2020-01-17 13:50:29) 
			//Assert.assertEquals(getWebElementXpath("ChartFlagPopupNote").getText().replace("\n", " "), "[DeltaCalculator 2020-01-17 13:50:29] System was unable to calculate the previous 6 values due to Data Error | System detected a catch-up spike. Estimate calculated with a weighted average profile.");
			Assert.assertTrue(getWebElementXpath("ChartFlagPopupNote").getText().replace("\n", " ").contains("System was unable to calculate the previous 6 values due to Data Error | System detected a catch-up spike. Estimate calculated with a weighted average profile."));
			Reporter.log("Verified one flag notes. ");
			
			getWebElementActionXpath("CloseDataPointDetailPopUp").click();
			Thread.sleep(1000);
			printLog("Verified data point detail pop up data in corrected mode.");
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ WeightedAvgProfile");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[50]");
			String correctedModeTableData[]="6/26/2019 09:30 AM~932.4~Valid+Note+Estimate|6/26/2019 09:45 AM~918.5~Valid+Note+Estimate|6/26/2019 10:00 AM~901.4~Valid+Note+Estimate|6/26/2019 10:15 AM~926.5~Valid+Note+Estimate|6/26/2019 10:30 AM~923.2~Valid+Note+Estimate|6/26/2019 10:45 AM~928.2~Valid+Note+Estimate|6/26/2019 11:00 AM~916.8~Valid+Note+Estimate".split("\\|");
			
			int i=0;
			if(checkBrowserHeadless) {
				i=38;
				movingTill=43;
				rowVal=i;
			}else {
				i=19;
				movingTill=25;
				rowVal=i;
			}

			String correctedRowWiseData[]=null;
			for(;i<=movingTill;i++) {
				correctedRowWiseData=correctedModeTableData[(i-rowVal)].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/th").getText(), correctedRowWiseData[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td[1]").getText(), correctedRowWiseData[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td[2]").getText(), correctedRowWiseData[2]);
			}
			printLog("Corrected mode functionality complated!!");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}
