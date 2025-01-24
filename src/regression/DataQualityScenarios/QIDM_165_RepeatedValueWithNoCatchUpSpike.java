package regression.DataQualityScenarios;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies if the data is corrected as expected for a site when there is a 'Repeated Value' 
 * (there is not accumulation in the values)
 * 
 * */
public class QIDM_165_RepeatedValueWithNoCatchUpSpike extends TestBase {
	LoginTC login = null;

	@Test
	public void repeatedValueWithNoCatchUpSpike() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to LoadProfileAnalysisCard
			goToPAMCard("LoadProfileAnalysisCard");

			// Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity", "Energy", standard);

			// Select Electric commodity for a site RepeatedValueWithNoCatchUpSpike
			searchSiteInLocationList("RepeatedValueWithNoCatchUpSpike");
			getWebElementXpath("DQElectricitySiteAfterFilter").click();
			Reporter.log("Searched for 'RepeatedValueWithNoCatchUpSpike' site");

			// Select the Fixed date range of July052019 and July062019
			addFixedDateRange(testData.getProperty("July052019"), testData.getProperty("July062019"));

			// Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			// Data quality flag on
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");

			// Load the chart
			refreshToLoadTheChart();
			
			//Include all data points radio button from chart option
			includeAllDataPointsOnAxesTabChartOption();

			// Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(),"QA Site 1 \\...\\ RepeatedValueWithNoCatchUpSpike kWh");
			printLog("Legend verified for Raw mode.");

			// Table data verification started
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);

			// Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(),	"QA Site 1 \\ Z_Data Quality Scenarios \\ RepeatedValueWithNoCatchUpSpike");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for Raw mode.");

			// Moving the cursor position to the table data where we have the test data to
			Utility.moveTheElement("//table[@id='maintable']//tr[20]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[50]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[45]");
			Thread.sleep(2000);
			
			// Enlarge the bottom panel
			enlargeBottomTabsPanel();
		
			int j = 26;
			int movingTill = 26;
			int rowVal = j;
				
			String rawModeTableData[] = "7/5/2019 09:15 PM~0~Valid|7/5/2019 09:30 PM~0~Valid|7/5/2019 09:45 PM~0~Valid|7/5/2019 10:00 PM~0~Valid|7/5/2019 10:15 PM~0~Valid|7/5/2019 10:30 PM~0~Valid"
					.split("\\|");
			String rawModeRowWiseData[] = null;
			for (; j <= movingTill; j++) {
				rawModeRowWiseData = rawModeTableData[(j - rowVal)].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + j + "]/th").getText(),
						rawModeRowWiseData[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + j + "]/td[1]").getText(),
						rawModeRowWiseData[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + j + "]/td[2]").getText(),
						rawModeRowWiseData[2]);
			}
			printLog("Raw mode functionality complated!!");

			// Enlarge the bottom panel
			hideEenlargeBottomTabsPanel();

			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");

			// Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(),
					"QA Site 1 \\...\\ RepeatedValueWithNoCatchUpSpike kWh");
			printLog("Legend verified for Corrected mode.");

			// Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(),
					"QA Site 1 \\ Z_Data Quality Scenarios \\ RepeatedValueWithNoCatchUpSpike");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(),
					testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(),
					testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");

			// Moving the cursor position to the table data where we have the test data to
			// verify
			Utility.moveTheElement("//table[@id='maintable']//tr[50]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[30]");
			Thread.sleep(2000);

			// Enlarge the bottom panel
			enlargeBottomTabsPanel();

			int i = 29;
			movingTill = 34;
			rowVal = i;
			String correctedModeTableData[] = "7/5/2019 09:15 PM~0~Valid|7/5/2019 09:30 PM~0~Valid|7/5/2019 09:45 PM~0~Valid|7/5/2019 10:00 PM~0~Valid|7/5/2019 10:15 PM~0~Valid|7/5/2019 10:30 PM~0~Valid"
					.split("\\|");

			String correctedRowWiseData[] = null;
			for (; i <= movingTill; i++) {
				correctedRowWiseData = correctedModeTableData[(i - rowVal)].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + i + "]/th").getText(),
						correctedRowWiseData[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + i + "]/td[1]").getText(),
						correctedRowWiseData[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[" + i + "]/td[2]").getText(),
						correctedRowWiseData[2]);
			}
			printLog("Corrected mode functionality complated!!");

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}
