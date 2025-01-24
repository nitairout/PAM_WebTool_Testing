package regression.ScatterPlotAnalysisCard;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies comparison date ranges options available in scatter plot one by one and verifies comparison data 
 * and date is displayed as expected for each option.
 */
public class QIDM_69_ScatterPlotAnalysisComparisonDateRanges extends TestBase{
	LoginTC login = null;
	
	@Test
	public void scatterPlotAnalysisComparisonDateRanges() throws Throwable {
		try {
			String defaultLegend="PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C";
			String energyTableHeader=testData.getProperty("TableHeaderEnergykWh");
			String temperatureHeader=testData.getProperty("TableHeaderTemperatureC");
			
			//Login into RA application with Internal user
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");

			//Selecting Energy and temperature measurements for PAMTestCapriataSaiwa
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Weather#Temperature~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			printLog("Selected Electricity Energy & Weather Tempearture with PAMTest_Capriata/Saiwa");
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			
			
			Utility.moveTheScrollToTheTop();
			Assert.assertEquals(getWebElementXpath_D("//span[@id='dateRangeStart']").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath_D("//span[@id='dateRangeEnd']").getText(), testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the date range on chart");
			Utility.moveTheScrollToTheDown();
			
			//Table data verification
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			String tableDataForJan2023 = "1/1/2023~9,459~14.28|1/2/2023~11,854~15.00";
			verifyTableDataWithExpected(tableDataForJan2023, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data and headers verification for JAN 2023");

			comparisionDateRange("Previous Year");
			//Verify the chart legends for previous year
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (Previous Year)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (Previous Year)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForPreviousYear = "1/1/2023~9,459~14.28~1/2/2022~10,050~13.10|1/2/2023~11,854~15.00~1/3/2022~9,711~13.33";
			verifyTableDataWithExpected(tableDataForPreviousYear, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Previous Year");
			
			comparisionDateRange("Previous Month");
			Thread.sleep(9000);
			//Verify the chart legends for previous Month
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (Previous Month)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (Previous Month)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForPreviousMonth = "1/1/2023~9,459~14.28~12/4/2022~9,281~10.23|1/2/2023~11,854~15.00~12/5/2022~5,726~12.50";
			verifyTableDataWithExpected(tableDataForPreviousMonth, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Previous Month");
			
			comparisionDateRange("Previous Week");
			Thread.sleep(7000);
			//Verify the chart legends for previous Week
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (Previous Week)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (Previous Week)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForPreviousWeek = "1/1/2023~9,459~14.28~12/25/2022~7,895~12.55|1/2/2023~11,854~15.00~12/26/2022~9,184~15.14";
			verifyTableDataWithExpected(tableDataForPreviousWeek, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Previous Week");
			
			
			comparisionDateRange("Previous Day");
			Thread.sleep(8000);
			//Verify the chart legends forPrevious Day
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (Previous Day)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (Previous Day)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForPreviousDay = "1/1/2023~9,459~14.28~12/31/2022~8,635~13.21|1/2/2023~11,854~15.00~1/1/2023~9,459~14.28";
			verifyTableDataWithExpected(tableDataForPreviousDay, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Previous Day");
			
			comparisionDateRange("Year Before Last");
			Thread.sleep(8000);
			//Verify the chart legends for Year Before Last
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (Year Before Last)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (Year Before Last)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForYearBeforeLast = "1/1/2023~9,459~14.28~1/3/2021~10,958~7.338|1/2/2023~11,854~15.00~1/4/2021~13,486~6.528";
			verifyTableDataWithExpected(tableDataForYearBeforeLast, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Year Before Last");
			
			comparisionDateRange("Select Start Date");
			Thread.sleep(2000);
			getWebElementActionXpath_D("//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input").click();
			Thread.sleep(1000);
			//pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input").sendKeys("6/1/2022");
			Thread.sleep(1000);
			getWebElementXpath_D("//*[@id='comparisonDateRange']//button/i[@class='fa fa-check']").click();
			Thread.sleep(8000);
			
			//Verify the chart legends for Select Start Date
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (6/1/2022)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (6/1/2022)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForSelectStartDatet = "1/1/2023~9,459~14.28~6/1/2022~9,439~21.23|1/2/2023~11,854~15.00~6/2/2022~9,228~22.31";
			verifyTableDataWithExpected(tableDataForSelectStartDatet, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Select Start Date");
			
			comparisionDateRange("Select Start and End Dates");
			Thread.sleep(2000);
			
			//Start Date
			getWebElementActionXpath_D("(//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input)[1]").click();
			Thread.sleep(1000);
			// pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("(//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input)[1]").sendKeys("5/1/2022");
			Thread.sleep(1000);
			
			//End Date
			getWebElementActionXpath_D("(//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input)[2]").click();
			Thread.sleep(1000);
			// pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("(//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input)[2]").sendKeys("5/31/2022");
			Thread.sleep(1000);
			
			getWebElementXpath_D("//*[@id='comparisonDateRange']//button/i[@class='fa fa-check']").click();
			Thread.sleep(8000);
			
			//Verify the chart legends for Year Before Last
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), defaultLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), defaultLegend+" (5/1/2022)");
			Assert.assertEquals(getWebElementXpath("ScatterLegendFour").getText(), defaultLegend+" (5/1/2022)");
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText());
			Assert.assertEquals(energyTableHeader, getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[3]").getText());
			Assert.assertEquals(temperatureHeader, getWebElementActionXpath_D("//table[@id='maintable']/thead/tr[2]/th[4]").getText());

			String tableDataForSelectStartandEndDates = "1/1/2023~9,459~14.28~5/1/2022~8,986~16.69|1/2/2023~11,854~15.00~5/2/2022~8,620~15.95";
			verifyTableDataWithExpected(tableDataForSelectStartandEndDates, 2, "QIDM_69_ScatterPlotAnalysisComparisonDateRanges");
			printLog("Table data,headers,legend verification for Select Start and End Dates");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}