package regression.AllCardsCommonTCs;

import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;

/*
 * This test case verifies card to card navigation from one card to another
 */

public class QIDM_42_CardToCardNavigation extends TestBase {
	LoginTC login = null;

	@Test
	public void cardToCardNavigation() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Trend Analysis card
			goToPAMCard("TrendAnalysisCard");
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "New Trend Analysis");
			// Select a site 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			// Click on Electric Commodity
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			// Select Fixed date range as 'Jan 1st 2023 to Jan 31st 2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			// All variable declaration
			String legend, actualStartChartDate, actualEndChartDate;

			legend = getWebElementXpath("ColumnLegendOne").getText();
			Assert.assertEquals(legend, testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Trend Analysis Card..");

			// Verify start and end date from chart
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, testData.getProperty("FixedDateStartJan012023"));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the start & end date for Trend Analysis Card..");

			// Navigate to Load Profile Analysis card using C2C
			card2Card("Load Profile Analysis");
			Thread.sleep(8000);
			// Verify the legend and start & end date from chart
			legend = getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legend, testData.getProperty("PAMTest_CapriataSaiwakWh"));
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, "1/1/2023 12:00 AM");
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, "2/1/2023 12:00 AM");
			printLog("Verification completed for Load Profile Analysis");

			// Navigate to Calendar Load Profile Analysis card
			card2Card("Calendar Load Profile Analysis");
			Thread.sleep(8000);
			boolean chart = getWebElementXpath("ClpChart").isDisplayed();
			Assert.assertTrue(chart);
			// verify the date from chart
			actualStartChartDate = getWebElementXpath("ClpChartMonth").getText();
			Assert.assertEquals(actualStartChartDate, "January 2023");
			printLog("Verification completed for Calendar Load Profile Analysis");

			// Navigate to Comparison Analysis card
			card2Card("Comparison Analysis");
			// Verify the legend and start & end date from chart
			legend = getWebElementXpath("ColumnLegendOne").getText();
			System.out.println("legend...."+legend);
			Assert.assertTrue(legend.replaceAll("\\n", " ").contains(testData.getProperty("PAMTest_CapriataSaiwakWh")));
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, testData.getProperty("FixedDateStartJan012023"));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, testData.getProperty("FixedDateEndJan312023"));
			printLog("Verification completed for Comparison Analysis");

			// Navigate to Scatter Plot Analysis card
			card2Card("Scatter Plot Analysis");
			Thread.sleep(8000);
			// Verify the legend and start & end date from chart
			legend = getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legend, testData.getProperty("PAMTest_CapriataSaiwakWh"));
			legend = getWebElementXpath("ScatterLegendTwo").getText();
			Assert.assertEquals(legend, testData.getProperty("PAMTest_CapriataSaiwakWh"));
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, testData.getProperty("FixedDateStartJan012023"));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, testData.getProperty("FixedDateEndJan312023"));
			printLog("Verification completed for Scatter Plot Analysis");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
