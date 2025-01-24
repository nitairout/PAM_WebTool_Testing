package regression.ComparisonAnalysisCard;

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
 * This test verifies comparison analysis 'Index By' options which includes index by RA metric and weather data. 
 * And also verifies warming message to the user when there is a missing index value. 
 * And also verifies sub site can access the index set at parent site.
 */

public class QIDM_78_ComparisonAnalysisIndexByOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void comparisonAnalysisIndexByOptions() throws Throwable {
		try {
			
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Comparison Analysis Card
			goToPAMCard("ComparisonAnalysisCard");
			
			// Search for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();Thread.sleep(1000);
			
			refreshToLoadTheChart();
			// Search for Site'PAMTest_Herentals, Biscuits' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();

			refreshToLoadTheChart();
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			//refreshToLoadTheChart();
			// Verify the legends on the Chart
			String legendName = getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " ");
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(legendName, "Energy kWh");
			Assert.assertEquals(legendName, "kWh");
			printLog("Verify the legends on the Chart.");
			
			// Select Site Metrics as 'AutoTest(Sq. Ft.)' from IndexBy and verify the legend
			indexBySelectMatrics("AutoTest(Sq. Ft.)");
			aJaxWait();
			legendName = getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " ");
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(legendName, "Energy kWh / Sq. Ft.");
			Assert.assertEquals(legendName, "kWh / Sq. Ft.");
			
			// Verified the headers on the table data
			String tableHeaders[] = {"Site","Energy (kWh)","AutoTest(Sq. Ft.)","Energy (kWh / Sq. Ft.)","Energy (kWh / Sq. Ft.) %"};
			ArrayList<String> holdTableHeaders = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders.size(); i++) {
				Assert.assertEquals(holdTableHeaders.get(i), tableHeaders[i]);
			}
			// Verified the data for the 3 rows from table Data
			String byTableData = "PAMTest_Herentals, Biscuits~3,746,006~2,050~1,827~94.58|PAMTest_Capriata/Saiwa~261,782~2,500~104.7~5.420|Total~4,007,788~~~";
			verifyTableDataWithExpected(byTableData,3,"QIDM_78_ComparisonAnalysisIndexByOptions");
			printLog("Verified the table data as expected");
			
			// Select Weather and Daily Indexes as 'CDD (°C)' from IndexBy and verify the legend
			getWebElementActionXpath("WeatherAndDailyIndex").click();
			Thread.sleep(5000);
			String index = "CDD (\u00B0C)";
			getWebElementActionXpath_D("//*[normalize-space()='"+index+"']").click();
			Thread.sleep(3000);
			
			//new Select(getWebElementActionXpath("WeatherAndDailyIndex")).selectByVisibleText("CDD (\u00B0C)");
			aJaxWait();
			legendName = getWebElementXpath("ColumnLegendOne").getText();
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(legendName.replaceAll("\\n", " "), "Energy kWh / Sq. Ft. / CDD (\u00B0C)");
			Assert.assertEquals(legendName.replaceAll("\\n", " "), "kWh / Sq. Ft. / CDD (\u00B0C)");
			String tableHeaders1[] = {"Site","Energy (kWh / Sq. Ft.)","CDD (\u00B0C)","AutoTest(Sq. Ft.)","Energy (kWh / Sq. Ft. / CDD (\u00B0C))","Energy (kWh / Sq. Ft. / CDD (\u00B0C)) %"};

			// Verified the headers on the table data
			ArrayList<String> holdTableHeaders1 = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders1.size(); i++) {
				Assert.assertEquals(holdTableHeaders1.get(i), tableHeaders1[i]);
			}
			// Verified the data for the 3 rows from table Data
			byTableData = "PAMTest_Capriata/Saiwa~261,782~0.000~2,500~104.7~5.420|PAMTest_Herentals, Biscuits~3,746,006~0.000~2,050~1,827~94.58|Total~4,007,788~~~~";
			verifyTableDataWithExpected(byTableData,3,"QIDM_78_ComparisonAnalysisIndexByOptions");
			printLog("Verified the table data as expected");
			
			//closing pop up
			List<WebElement> popup = d.findElements(By.xpath("//div[@class='alert alert-danger alert-dismissable']/button[@class='close']/span[1]"));
			if(popup.size()==1) {
				getWebElementXpath_D("//div[@class='alert alert-danger alert-dismissable']/button[@class='close']/span[1]").click();
			}
			Utility.moveTheScrollToTheTop();			
			//Select the Pie-Chart and Verify the legend names
			if(!checkBrowserHeadless) {
				getWebElementActionXpath("PieChart").click();
				aJaxWait();
			}
			String pieLegendOneName = getWebElementXpath("PieChartLegendOne").getText();
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(pieLegendOneName.replaceAll("\\n", " "), "PAMTest_Herentals, Biscuits Energy kWh / Sq. Ft. / CDD (\u00B0C)");
			Assert.assertEquals(pieLegendOneName.replaceAll("\\n", " "), "PAMTest_Herentals, Biscuits kWh / Sq. Ft. / CDD (\u00B0C)");
			String pieLegendTwoName = getWebElementXpath("PieChartLegendTwo").getText();
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(pieLegendTwoName.replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa Energy kWh / Sq. Ft. / CDD (\u00B0C)");
			Assert.assertEquals(pieLegendTwoName.replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa kWh / Sq. Ft. / CDD (\u00B0C)");
			
			// Verified the headers on the table data
			holdTableHeaders1 = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders1.size(); i++) {
				Assert.assertEquals(holdTableHeaders1.get(i), tableHeaders1[i]);
			}
			// Select Weather and Daily Indexes as 'None' from IndexBy
			getWebElementActionXpath("WeatherAndDailyIndex").click();
			Thread.sleep(5000);
			getWebElementActionXpath_D("//*[normalize-space()='None']").click();
			Thread.sleep(3000);
			//new Select(getWebElementActionXpath("WeatherAndDailyIndex")).selectByVisibleText("None");
			aJaxWait();
			// Select Site Metrics as 'AutoTest_MissingIndex(Sq. Ft.)' from IndexBy and verify the legend
			indexBySelectMatrics("AutoTest_MissingIndex(Sq. Ft.)");
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			//Verify the alert message
			popup = d.findElements(By.xpath("//div[contains(@class,'alert')]/span"));
			if(popup.size()==1) {
				Assert.assertEquals(getWebElementXpath_D("//div[contains(@class,'alert')]/span").getText(),"One or more index values is either missing for this time range or incompatible with the time interval.");
			}else {
				Assert.fail("Alert message is not displaying");
			}
			
			// Verified the data for the 3 rows from table Data
			byTableData = "PAMTest_Capriata/Saiwa~261,782~~~|PAMTest_Herentals, Biscuits~3,746,006~~~|Total~4,007,788~~~";
			verifyTableDataWithExpected(byTableData,3,"QIDM_78_ComparisonAnalysisIndexByOptions");
			printLog("Verified the table data as expected");
			
			// unselect for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			// unselect for Site'PAMTest_Herentals, Biscuits' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();			
			refreshToLoadTheChart();
			// Search for Site'PAMTest_Capriata/Saiwa / PAMTest_Main Meter' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			
			refreshToLoadTheChart();
			
			// Select Site Metrics as 'AutoTest(Sq. Ft.)' from IndexBy and verify the legend
			indexBySelectMatrics("AutoTest(Sq. Ft.)");
			aJaxWait();
			
			// Verified the headers on the table data
			String tableHeaders2[] = {"Site","Energy (kWh)","AutoTest(Sq. Ft.)","Energy (kWh / Sq. Ft.)","Energy (kWh / Sq. Ft.) %"};
			ArrayList<String> holdTableHeaders2 = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders2.size(); i++) {
				Assert.assertEquals(holdTableHeaders2.get(i), tableHeaders2[i]);
			}
			// Verified the data for the 3 rows from table Data
			String byTableData1 = "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter~261,782~2,500~104.7~100.0|Total~261,782~~~";
			verifyTableDataWithExpected(byTableData1,2,"QIDM_78_ComparisonAnalysisIndexByOptions");
			printLog("Verified the table data as expected");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
