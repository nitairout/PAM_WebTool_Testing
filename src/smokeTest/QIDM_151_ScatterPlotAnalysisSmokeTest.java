package smokeTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies very basic functionality of 'Scatter Plot Analysis Card'  to make sure card is working as expected after new builds are deployed.
 * It verifies if chart is loaded with default configurations, verifies data is there on chart and table tab.  
 */
public class QIDM_151_ScatterPlotAnalysisSmokeTest extends TestBase {
	LoginTC login = null;

	@Test
	public void scatterPlotAnalysisSmokeTest() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("ScatterPlotCard");

			// Verify the default message
			Assert.assertTrue(d.getPageSource().contains(testData.getProperty("defaultMessage")));

			// Verify the widget title as 'New Scatter Plot Analysis'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "New Scatter Plot Analysis");
			printLog("Verified the card title as 'New Scatter Plot Analysis'");

			String[] expectedDefaultMeasuremets = { "electricity-Energy", "gas-Volume", "water-Volume", "steam-Energy", "weather-Temperature" };
			
			// Click on electric commodity icon to enter into select measurement pop up
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();

			// Verify the default measurements
			List<WebElement> defaultMeasurements = d.findElements(By.xpath("//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-list-item')]"));
			Assert.assertEquals(defaultMeasurements.size(), 5);

			for (int i = 0; i < expectedDefaultMeasuremets.length; i++) {
				String measurementXpath="(//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-name')])["+(i+1)+"]/span";
				Assert.assertEquals(getWebElementActionXpath_D(measurementXpath).getText(), (expectedDefaultMeasuremets[i].split("-")[1]));
				Assert.assertTrue(d.findElement(By.xpath("(//div[contains(@class,'measurement-list-item')]/child::i[1])["+(i+1)+"]")).getAttribute("class").contains((expectedDefaultMeasuremets[i].split("-")[0])));
			}
			printLog("Verified the default measurements available for the Scatter Plot card");
			
			// Click on save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);

			// Search with site testData.getProperty("PAMTestCapriataSaiwa")
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			// Click on the measurements
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();

			refreshToLoadTheChart();

			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate, actualStartChartDate, actualEndChartDate;

			// Default last 30 days chart data
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today);
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(29));
			
			// verify start and end date from chart
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			printLog("Verified the Scatter Plot Card Legends");

			// First 4 rows of table data verification
			ArrayList<ArrayList<String>> holdPamTableData = Utility.returnPamTableData(4);
			ArrayList<String> holdRowWiseData = null;
			for (int i = 0; i < holdPamTableData.size(); i++) {
				holdRowWiseData = holdPamTableData.get(i);
				Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("M/d/yyyy", today.minusDays((29 - i))));
			}
			printLog("Verified first 4 rows of table date for default 30 days");

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}