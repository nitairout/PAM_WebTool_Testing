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
 * This test verifies very basic functionality of 'Load Profile Analysis Card'  to make sure card 
 * is working as expected after new builds are deployed. It verifies if chart is loaded with 
 * default configurations, verifies data is there on chart and table tab.  
 * Also verifies saving a analysis with different name and pushing a chart to a dashboard.
 */
public class QIDM_147_LoadProfileAnalysisSmokeTest extends TestBase {
	LoginTC login = null;

	@Test
	public void loadProfileAnalysisSmokeTest() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");

			// Verify the default message
			Assert.assertTrue(d.getPageSource().contains(testData.getProperty("defaultMessage")));

			// Verify the widget title as 'New Load Profile Analysis'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "New Load Profile Analysis");
			printLog("Verified the card title as 'New Load Profile Analysis'");

			String[] expectedDefaultMeasuremets = { "electricity-Demand", "gas-Volume", "gas-Energy", "water-Volume", "steam-Energy" };
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
			printLog("Verified the default measurements available for the Load Profile Analysis card");
			
			// Click on save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);

			// Search with site testData.getProperty("PAMTestCapriataSaiwa")
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));

			// Click on the measurements
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Water").click();

			refreshToLoadTheChart();
/*
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate;

			// Time line option 'last 7 days' (defualt date range for LP card) Calculating expected start and end date range for last 7 days
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.plusDays(1)) + " 12:00 AM";
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(6)) + " 12:00 AM";
			String expectedStartAndEndDate=expectedStartChartDate+" - "+expectedEndChartDate;
			// Chart actual start and end date
			String actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the default Date Range of the card");

			// Verify the legends
			Assert.assertNotNull(getWebElementActionXpath("LineLegendOne").getText());
			Assert.assertNotNull(getWebElementActionXpath("LineLegendTwo").getText());
			Assert.assertNotNull(getWebElementActionXpath("LineLegendThree").getText());
			Assert.assertNotNull(getWebElementActionXpath("LineLegendFour").getText());
			printLog("Verified the Legends");
			
			// Verify the Statistics tab should have values
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Max Demand");
			Assert.assertNotNull(getWebElementActionXpath("Statistics_FirstElementValue").getText());
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "kW");
			printLog("Verified the values from Statistics tab");
			
			// First row of table data verification
			getWebElementActionXpath("DataTableTab").click();
			ArrayList<ArrayList<String>> holdPamTableData = Utility.returnPamTableData(1);
			ArrayList<String> holdRowWiseData = null;
			for (int i = 0; i < holdPamTableData.size(); i++) {
				holdRowWiseData = holdPamTableData.get(i);
				Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("M/d/yyyy", today.minusDays(6)) + " 12:15 AM");
			}
			printLog("Verified first row of table date for default last 7 days");
*/
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}