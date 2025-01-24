package regression.DataQualityScenarios;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies if the data is corrected as expected for a site where there is a
 *  'Repeated Value with Catch-up spike' (No accumulation in the values)
 * */
public class QIDM_163_RepeatedValueWithCatchUpSpike extends TestBase {
	LoginTC login = null;
	@Test
	public void repeatedValueWithCatchUpSpike() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			// Navigate to LoadProfileAnalysisCard
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Select Electric commodity for a site RepeatedValueWithCatchUpSpike
			searchSiteInLocationList("RepeatedValueWithCatchUpSpike");
			getWebElementXpath("DQElectricitySiteAfterFilter").click();
			Reporter.log("Searched for 'RepeatedValueWithCatchUpSpike' site");

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
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ RepeatedValueWithCatchUpSpike kWh");
			printLog("Legend verified for Raw mode.");
			
			//Table data verification started
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ RepeatedValueWithCatchUpSpike");
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
			int movingTill=26;
			int rowVal=j;
				
			String rawModeTableData[]="7/5/2019 09:15 PM~0~Suspect+Note|7/5/2019 09:30 PM~0~Suspect+Note|7/5/2019 09:45 PM~0~Suspect+Note|7/5/2019 10:00 PM~0~Suspect+Note|7/5/2019 10:15 PM~0~Suspect+Note|7/5/2019 10:30 PM~0~Suspect+Note|7/5/2019 10:45 PM~700.0~Suspect+Note".split("\\|");
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
			Thread.sleep(8000);
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ RepeatedValueWithCatchUpSpike kWh");
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification 
			WebElement icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			String iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 7 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			//Check the number of flags present in chart
			int flagSize=getSizeOfElement(locators.getProperty("DataQualityFlagsOnCorectMode"),"xpath");
			Assert.assertEquals(flagSize, 7);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ RepeatedValueWithCatchUpSpike");
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
			movingTill=34;
			rowVal=i;
			String correctedModeTableData[]="7/5/2019 09:15 PM~100.0~Valid+Note+Estimate|7/5/2019 09:30 PM~100.0~Valid+Note+Estimate|7/5/2019 09:45 PM~100.0~Valid+Note+Estimate|7/5/2019 10:00 PM~100.0~Valid+Note+Estimate|7/5/2019 10:15 PM~100.0~Valid+Note+Estimate|7/5/2019 10:30 PM~100.0~Valid+Note+Estimate|7/5/2019 10:45 PM~100.0~Valid+Note+Estimate".split("\\|");
			
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
