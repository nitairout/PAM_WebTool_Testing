package regression.ComparisonAnalysisCard;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies data breakdown by submeters in comparison analysis card and also pushes chart to widget 
 * and verifies widget is displayed same as PAM card
 */

public class QIDM_76_ComparisonAnalysisBreakdownBySubmeters extends TestBase {

	LoginTC login = null;

	@Test
	public void comparisonAnalysisBreakdownBySubmeters() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
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
			// Selected Fixed date range '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			//refreshToLoadTheChart();
			
			// Select the 'Submeters' from DataBreakdown
			dataBreakDownSubMeter("Submeters");

			//refreshToLoadTheChart();
			Thread.sleep(30000);//added more time to load the legends
			// Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			Assert.assertEquals(sitesList[0], "PAMTest_Herentals, Biscuits");
			Assert.assertEquals(sitesList[1], testData.getProperty("PAMTestCapriataSaiwa"));
			printLog("Verified the sites on the chart as " + sitesList);

			String actualLegendName;
			String[] expectedLegendNamesOnBarChart = { "PAMTest_Energy Balance kWh", "PAMTest_Main Meter kWh" };

			// Verify the legend name on bar chart
			actualLegendName = getWebElementXpath("ColumnLegendOne").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamesOnBarChart[0]);

			actualLegendName = getWebElementXpath("ColumnLegendTwo").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamesOnBarChart[1]);

			printLog("Verify the legend name on bar chart has completed");

			// Verified the headers on the table Data
			String tableHeaders[] = { "Site", "PAMTest_Energy Balance - Energy (kWh)", "PAMTest_Energy Balance - Energy (kWh) %", "PAMTest_Main Meter - Energy (kWh)", "PAMTest_Main Meter - Energy (kWh) %", "Total Energy (kWh)" };
			ArrayList<String> holdTableHeaders = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders.size(); i++) {
				Assert.assertEquals(holdTableHeaders.get(i), tableHeaders[i]);
			}
			printLog("Verify the headers on the table has completed");

			// Verified the data for the 3 rows from table Data
			String expectedSubMeterTableData="PAMTest_Herentals, Biscuits~647,967~13.18~3,746,006~76.18~4,393,973|PAMTest_Capriata/Saiwa~261,782~5.323~261,782~5.323~523,564|Total~909,749~18.50~4,007,788~81.50~4,917,537";
			verifyTableDataWithExpected(expectedSubMeterTableData,3,"QIDM_76_ComparisonAnalysisBreakdownBySubmeters");
			printLog("Verification of Table Data is completed on bar chart!!");

			// Select the Pie-Chart and
			Utility.moveTheScrollToTheTop();
			if(!checkBrowserHeadless) {
				getWebElementActionXpath("PieChart").click();
				Thread.sleep(2000);
			}
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//String[] expectedLegendNamePieChart = { "PAMTest_Herentals, Biscuits PAMTest_Energy Balance kWh", "PAMTest_Herentals, Biscuits PAMTest_Energy Balance kWh",testData.getProperty("PAMTest_CapriataSaiwakWh")};
			String[] expectedLegendNamePieChart = { "PAMTest_Herentals, Biscuits \\ PAMTest_Main Meter kWh","PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance kWh", "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh","PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance kWh"};
			// Verify the legend names on pie chart
			actualLegendName = getWebElementXpath("PieChartLegendOne").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[0]);

			actualLegendName = getWebElementXpath("PieChartLegendTwo").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[1]);

			actualLegendName = getWebElementXpath("PieChartLegendThree").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[2]);

			actualLegendName = getWebElementXpath("PieChartLegendFour").getText();
			actualLegendName = actualLegendName.replaceAll("[\\n\\r]", " ");
			Assert.assertEquals(actualLegendName, expectedLegendNamePieChart[3]);

			printLog("Verify the legend name on pie chart has completed");
			
			// Verified the data for the 3 rows from table Data
			verifyTableDataWithExpected(expectedSubMeterTableData,3,"QIDM_76_ComparisonAnalysisBreakdownBySubmeters");
			printLog("Verification of Table Data is completed on Pie chart!!");
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(15000);
			
			//Click on Table tab
			getWebElementXpath_D("//gridster/gridster-item[1]//div[@class='wc-wrapper']//a[text()='Table']").click();
			Thread.sleep(1000);
			printLog("Click on Table tab on 'Comparison Widget'");
			
			//1st 3 rows of table data verification
			verifyTableDataWithExpected(expectedSubMeterTableData,3,"QIDM_76_ComparisonAnalysisBreakdownBySubmeters");
			printLog("Verification of Table Data is completed on dashbaord widget!!");

			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}