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
 * This test verifies if the data is corrected as expected for a site/meter where 
 * it has a Gap (a missing interval without time stamp and value).
 * 
 * */
public class QIDM_160_GapWithNoCatchUpSpike extends TestBase {
	LoginTC login = null;
	@Test
	public void gapWithNoCatchUpSpike() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			// Navigate to LoadProfileAnalysisCard
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Select Electric commodity for a site GapWithNoCatchUpSpike
			searchSiteInLocationList("GapWithNoCatchUpSpike");
			getWebElementXpath("DQElectricitySiteAfterFilter").click();
			Reporter.log("Searched for 'GapWithNoCatchUpSpike' site");

			// Select the Fixed date range of July052019 and July062019
			addFixedDateRange(testData.getProperty("July052019"), testData.getProperty("July062019"));
			
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
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ GapWithNoCatchUpSpike kWh");
			printLog("Legend verified for Raw mode.");
			
			//Check the number of flags and content
			String flagXpath=locators.getProperty("DataQualityFlagsOnCorectMode");
			int flagSize=getSizeOfElement(flagXpath,"xpath");
			Assert.assertEquals(flagSize, 1);
			getWebElementActionXpath_D(flagXpath).click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementActionXpath("ChartFlagPopUpHeaderSiteName").getText(), "QA Site 1 \\...\\ GapWithNoCatchUpSpike");
			getWebElementActionXpath("CloseDataPointDetailPopUp").click();
			Thread.sleep(1000);
			printLog("Verified RAW data point detail pop up data.");
			
			//Table data verification started
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ GapWithNoCatchUpSpike");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for Raw mode.");
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[20]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[50]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[45]");
			Thread.sleep(2000);
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			int j=26;
			int movingTill=31;
			int rowVal=j;
				
			String rawModeTableData[]="7/5/2019 09:15 PM~~Unknown|7/5/2019 09:30 PM~~Unknown|7/5/2019 09:45 PM~~Unknown|7/5/2019 10:00 PM~~Unknown|7/5/2019 10:15 PM~~Unknown|7/5/2019 10:30 PM~160.0~Suspect+Note".split("\\|");
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
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ GapWithNoCatchUpSpike kWh");
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification 
			WebElement icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			String iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 5 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			//Check the number of flags present in chart
			flagSize=getSizeOfElement(locators.getProperty("DataQualityFlagsOnCorectMode"),"xpath");
			Assert.assertEquals(flagSize, 6);
			
			//Verify the data into pop up. clicking on 6th flag
			getWebElementActionXpath_D("(//*[contains(@class,'highcharts-point')])[5]").click();
			aJaxWait();
			Assert.assertEquals(getWebElementActionXpath("ChartFlagPopUpHeaderSiteName").getText(), "QA Site 1 \\...\\ GapWithNoCatchUpSpike");
			getWebElementActionXpath("CloseDataPointDetailPopUp").click();
			Thread.sleep(1000);
			printLog("Verified data point detail pop up data in corrected mode.");
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ GapWithNoCatchUpSpike");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");
			
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[50]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[30]");
			Thread.sleep(2000);

			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			int i=29;
			movingTill=29;
			rowVal=i;
			String correctedModeTableData[]="7/5/2019 09:15 PM~0~Suspect+Note+Estimate|7/5/2019 09:30 PM~0~Suspect+Note+Estimate|7/5/2019 09:45 PM~0~Suspect+Note+Estimate|7/5/2019 10:00 PM~0~Suspect+Note+Estimate|7/5/2019 10:15 PM~0~Suspect+Note+Estimate|7/5/2019 10:30 PM~160.0~Suspect+Note".split("\\|");
			
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