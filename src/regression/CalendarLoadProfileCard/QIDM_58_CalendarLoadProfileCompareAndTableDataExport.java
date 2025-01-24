package regression.CalendarLoadProfileCard;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates the calendar load profile card basic functionality including verifying data in table and exporting the data.
 */

public class QIDM_58_CalendarLoadProfileCompareAndTableDataExport extends TestBase {
	LoginTC login = null;

	@Test
	public void calendarLoadProfileCompareAndTableDataExport() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Calendar Load Profile Analysis Card
			goToPAMCard("CalendarAnalysisCard");
			// Search for Site'PAMTest_Trasformatore 1' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestTrasformatore1"));

			// Add the measurements Demand and Temperature for the site
			Utility.selectMultipleMeasurements("Electricity#Demand~standard*Energy~standard|Weather#Temperature~standard");
			// Select the Demand and Temperature Measurements
			getWebElementXpath("PAMTest_Trasformatore1_Energy").click();
			getWebElementXpath("PAMTest_Trasformatore1_Weather").click();
			printLog("Selected Demand and Temperture for PAMTestTrasformatore1");

			// Select Jan 2023 against Fixed period
			fixedDateRangeForCLP("January", "2023");

			refreshToLoadTheChart();

			// Verify the Chart month and legends
			Assert.assertEquals(getWebElementXpath("ClpChartMonth").getText(), "January 2023");
			String chartLegend1 = d.findElement(By.xpath("(//span[@class='legend-text--calendar']/span)[1]")).getText();
			String chartLegend2 = d.findElement(By.xpath("(//span[@class='legend-text--calendar']/span)[2]")).getText();
			String chartLegend3 = d.findElement(By.xpath("(//span[@class='legend-text--calendar']/span)[3]")).getText();
			Assert.assertEquals(chartLegend1, "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(chartLegend3, "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kWh");
			Assert.assertEquals(chartLegend2, "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 \u00B0C");
			printLog("Verify the legends on the Chart.");

			// Select first two Mondays on the chart
			WebElement tableElement = d.findElement(By.xpath("(//*[@class='highcharts-series-group'])[2]"));
			Actions teAction = new Actions(d);
			teAction.moveToElement(tableElement,1,1).moveToElement(tableElement,1,1).click().build().perform();
			Thread.sleep(5000);
			
			tableElement = d.findElement(By.xpath("(//*[@class='highcharts-series-group'])[9]"));
			teAction = new Actions(d);
			teAction.moveToElement(tableElement,1,1).moveToElement(tableElement,1,1).click().build().perform();
			Thread.sleep(5000);

			// Verify the legends on the Compare tab
			Utility.moveTheScrollToTheDown();
			/*Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 \u00B0C");
			Assert.assertEquals(getWebElementXpath("LineLegendFour").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementXpath("LineLegendFive").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 \u00B0C");
			Assert.assertEquals(getWebElementXpath("LineLegendSix").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kWh");*/
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "Jan 2 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 \u00B0C");
			
			Assert.assertEquals(getWebElementXpath("LineLegendFour").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementXpath("LineLegendFive").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 \u00B0C");
			Assert.assertEquals(getWebElementXpath("LineLegendSix").getText(), "Jan 9 2023 PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kWh");
			printLog("Verify the legends on the Compare tab ");

			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);

			// Delete "calendar.csv" file if exists
			Utility.deleteDownloadFiles("calendar", ".csv");

			// Select "Export Chart Data to CSV" from chart menu options to download "calendar.csv" file
			kabobMenuOptions("Export Chart Data to CSV");

			// Get the data from CSV file
			ArrayList<List<String>> csvDataToCheckTheMsg = Utility.returnNumberOfRowsFromcsv("calendar", 7);
			List<String> firstRowData = null;

			// Get the data from Table data
			ArrayList<ArrayList<String>> tabledata = Utility.returnPamTableData(4);
			ArrayList<String> tableCellData = null;

			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				firstRowData = csvDataToCheckTheMsg.get(3 + i);

				Assert.assertEquals(tableCellData.get(0), firstRowData.get(0));
				Assert.assertEquals(tableCellData.get(1), firstRowData.get(1));
				Assert.assertEquals(tableCellData.get(2), firstRowData.get(2));
				Assert.assertEquals(tableCellData.get(3), firstRowData.get(3));

				tableCellData = null;
				firstRowData = null;
			}
			printLog("Verified the table data and csv data as expected");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}
