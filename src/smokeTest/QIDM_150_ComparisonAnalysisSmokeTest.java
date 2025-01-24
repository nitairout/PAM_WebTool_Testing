package smokeTest;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies very basic functionality of 'Comparison Analysis Card' to make sure card is working
 * as expected after new builds are deployed. It verifies if chart is loaded with default configurations, 
 * verifies data is there on chart and table tab. Also verifies saving a analysis with different name 
 * and pushing a chart to a dashboard.
 */
public class QIDM_150_ComparisonAnalysisSmokeTest extends TestBase {
	LoginTC login = null;

	@Test
	public void comparisonAnalysisSmokeTest() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("ComparisonAnalysisCard");

			// Verify the default message
			Assert.assertTrue(d.getPageSource().contains(testData.getProperty("defaultMessage")));

			// Verify the widget title as 'New Comparison Analysis'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "New Comparison Analysis");
			printLog("Verified the card title as 'New Comparison Analysis'");
			
			String[] expectedDefaultMeasuremets = { "electricity-Energy" };
			// Click on electric commodity icon to enter into select measurement pop up
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();
			// Verify the default measurements
			List<WebElement> defaultMeasurements = d.findElements(By.xpath("//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-list-item')]"));
			Assert.assertEquals(defaultMeasurements.size(), 1);

			for (int i = 0; i < expectedDefaultMeasuremets.length; i++) {
				String measurementXpath="(//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-name')])["+(i+1)+"]/span";
				Assert.assertEquals(getWebElementActionXpath_D(measurementXpath).getText(), (expectedDefaultMeasuremets[i].split("-")[1]));
				Assert.assertTrue(d.findElement(By.xpath("(//div[contains(@class,'measurement-list-item')]/child::i[1])["+(i+1)+"]")).getAttribute("class").contains((expectedDefaultMeasuremets[i].split("-")[0])));
			}
			printLog("Verified the default measurements available for the Comparison Analysis card");
			
			// Click on save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);

			// Search with site testData.getProperty("PAMTestCapriataSaiwa") and click on Energy measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();

			// Search with site testData.getProperty("PAMTestNapervilleIL") and click on Energy measurement
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();

			refreshToLoadTheChart();

			LocalDate today = LocalDate.now();
			String actualStartChartDate = getWebElementXpath_D("//div[@class='chart-timeRange']/descendant::span[1]").getText().trim();
			Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfYear())));
			String actualEndChartDate = getWebElementXpath_D("//div[@class='chart-timeRange']/descendant::span[2]").getText().trim();
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verified the default Date Range of the card");

			// Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			List<String> LegendArray=Arrays.asList(sitesList);
			Assert.assertTrue(LegendArray.contains(testData.getProperty("PAMTestNapervilleIL")));
			Assert.assertTrue(LegendArray.contains(testData.getProperty("PAMTestCapriataSaiwa")));
			printLog("Verified the sites on the chart as " + sitesList);
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "kWh");
			printLog("Verified the Comparison Analysis Card Legends");
			
			// Verified the headers on the table Data
			String tableHeaders[] = { "Site", "Energy (kWh)", "Energy (kWh) %" };
			ArrayList<String> holdTableHeaders = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders.size(); i++) {
				Assert.assertEquals(holdTableHeaders.get(i), tableHeaders[i]);
			}
			printLog("Verify the headers on the table has completed");

			// Verified the data from table Data
			Assert.assertTrue(LegendArray.contains(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText()));
			Assert.assertTrue(LegendArray.contains(getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/th").getText()));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[3]/th").getText(), "Total");
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[1]").getText());
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/td[1]").getText());
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[3]/td[1]").getText());
			printLog("Verified the table data as expected");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}