package regression.ComparisonAnalysisCard;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates the Breakdown by 'Site schedule' in comparison Analysis card. 
 * Checks site schedule periods are displayed for both sites as expected.
 */

public class QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule extends TestBase {
	LoginTC login = null;

	@Test
	public void comparisonAnalysisBreakdownBySiteSchedule() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Comparison Analysis Card
			goToPAMCard("ComparisonAnalysisCard");

			// Search for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();

			refreshToLoadTheChart();
			
			// Search for Site'PAMTest_Herentals, Biscuits' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			
			refreshToLoadTheChart();
			
			// Select the Fixed date range of '01/01/2023' - '31/01/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));

			//refreshToLoadTheChart();

			// Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			Assert.assertEquals(sitesList[0], "PAMTest_Herentals, Biscuits");
			Assert.assertEquals(sitesList[1], testData.getProperty("PAMTestCapriataSaiwa"));
			printLog("Verified the sites on the chart as " + sitesList);

			// Selected AutoTestTemplate from Site Schedule from DataBreakdown
			dataBreakDownSiteSchedule("Site Schedule","AutoTestTemplate");
			printLog("Selected AutoTestTemplate fom Site Schedule from DataBreakdown");
			Thread.sleep(12000);
			
			String[] expectedLegendNameBarChart = { "kWh (Closed)", "kWh (Open)" };
			// Verified the legend names on bar chart
			String actualLegendName = getWebElementXpath("ColumnLegendOne").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNameBarChart[0]);

			actualLegendName = getWebElementXpath("ColumnLegendTwo").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNameBarChart[1]);
			printLog("Verified the legend names on bar chart");

			// Verified the headers on the table data
			String expectedTableHeaders[] = { "Site", "Closed - Energy (kWh)", "Closed - Energy (kWh) %", "Open - Energy (kWh)", "Open - Energy (kWh) %", "Total Energy (kWh)" };
			ArrayList<String> actualTableHeaders = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < actualTableHeaders.size(); i++) {
				Assert.assertEquals(actualTableHeaders.get(i), expectedTableHeaders[i]);
			}
			printLog("Verified the headers on the table data");

			// Verified the data for the 3 rows from table Data
			String byTableData = "PAMTest_Herentals, Biscuits~2,626,111~65.53~1,119,895~27.94~3,746,006|PAMTest_Capriata/Saiwa~199,696~4.983~62,086~1.549~261,782|Total~2,825,807~70.51~1,181,981~29.49~4,007,788";
			//String byTableData = "PAMTest_Herentals, Biscuits~2,626,110~65.53~1,119,900~27.94~3,746,010|PAMTest_Capriata/Saiwa~199,696~4.983~62,086~1.549~261,782|Total~2,825,810~70.51~1,181,980~29.49~4,007,790";
			verifyTableDataWithExpected(byTableData,3,"QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule");
			printLog("Verified the table data as expected");

			// Select the Pie-Chart
			Utility.moveTheScrollToTheTop();
			if(!checkBrowserHeadless) {
				getWebElementActionXpath("PieChart").click();
				Thread.sleep(2000);
			}
			
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//String[] expectedLegendNamePieChart = { testData.getProperty("PAMTest_HerentalsBiscuitskWh"), testData.getProperty("PAMTest_HerentalsBiscuitskWh"),"PAMTest_Capriata/Saiwa Closed Energy kWh", "PAMTest_Capriata/Saiwa Open Energy kWh" };
			//String[] expectedLegendNamePieChart = { "PAMTest_Herentals, Biscuits Closed kWh", "PAMTest_Herentals, Biscuits Open kWh","PAMTest_Capriata/Saiwa Closed kWh", "PAMTest_Capriata/Saiwa Open kWh" };
			String[] expectedLegendNamePieChart = { "PAMTest_Herentals, Biscuits kWh (Closed)", "PAMTest_Herentals, Biscuits kWh (Open)","PAMTest_Capriata/Saiwa kWh (Closed)", "PAMTest_Capriata/Saiwa kWh (Open)" };

			// Verify the legend names on pie chart
			actualLegendName = getWebElementXpath("PieChartLegendOne").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[0]);

			actualLegendName = getWebElementXpath("PieChartLegendTwo").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[1]);

			actualLegendName = getWebElementXpath("PieChartLegendThree").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[2]);

			actualLegendName = getWebElementXpath("PieChartLegendFour").getText();
			actualLegendName = actualLegendName.replaceAll("\\n", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[3]);
			printLog("Verified the legend name on pie chart");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}