package regression.TrendAnalysisCard;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;

/*
 * This test verifies all the comparison date range options available in Trend card. Selects each option one by one and loads chart for 
 * default Energy measurement and verifies chart is loaded as expected by verifying the comparison date range on the top.
 * */

public class QIDM_62_TrendAnalysisComparisonDateRanges extends TestBase{
	LoginTC login = null;
	
	@Test
	public void trendAnalysisComparisonDateRanges() throws Throwable {
		try {
			String deaultLegend=testData.getProperty("PAMTest_CapriataSaiwakWh");
			String legendNamePreviousYear = deaultLegend+" (Previous Year)";
			String legendNamePreviousMonth = deaultLegend+" (Previous Month)";
			String legendNamePreviousWeek = deaultLegend+" (Previous Week)";
			String legendNamePreviousDay = deaultLegend+" (Previous Day)";
			String legendNameYearBeforeLast = deaultLegend+" (Year Before Last)";
			String legendNameVarianceFromPreviousYear = deaultLegend+" (Variance from Previous Year)";
			String legendNameVarianceFromPreviousMonth = deaultLegend+" (Variance from Previous Month)";
			String legendNameVarianceFromPreviousWeek = deaultLegend+" (Variance from Previous Week)";
			String legendNameVarianceFromPreviousDay = deaultLegend+" (Variance from Previous Day)";
			String legendNameVarianceFromYearBeforeLast = deaultLegend+" (Variance from Year Before Last)";
			String legendNameVariance = "PAMTest_Capriata/Saiwa kWh (Variance from 1/1/2022)";
			String columnLegend = deaultLegend+" (1/1/2022)";
			
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			//Select Electric commodity for a site PAMTest_Capriata/Saiwa
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), deaultLegend);
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//1st 4 rows table data verification for default chart load By Day
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			String byDayTableData="1/1/2023~9,459|1/2/2023~11,854|1/3/2023~3,847|1/4/2023~2,246";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("By day interval verification completed!!");
			
			//Select the Previous year as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Previous Year");
			
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), deaultLegend);
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), legendNamePreviousYear);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVarianceFromPreviousYear);
			
			String previousYearExpectedData="1/1/2023~9,459~1/2/2022~10,050~-591.0~-5.881|1/2/2023~11,854~1/3/2022~9,711~2,143~22.07|1/3/2023~3,847~1/4/2022~7,099~-3,252~-45.81|1/4/2023~2,246~1/5/2022~4,949~-2,703~-54.62";
			verifyTableDataWithExpected(previousYearExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Previous year Data tab values!!");
			
			//Select the Previous Month as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Previous Month");
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), legendNamePreviousMonth);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVarianceFromPreviousMonth);

			String previousMonthExpectedData="1/1/2023~9,459~12/4/2022~9,281~178.0~1.918|1/2/2023~11,854~12/5/2022~5,726~6,128~107.0|1/3/2023~3,847~12/6/2022~3,742~105.0~2.806|1/4/2023~2,246~12/7/2022~3,311~-1,065~-32.17";
			verifyTableDataWithExpected(previousMonthExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Previous Month Data tab values!!");
			
			//Select the Previous week as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Previous Week");
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), legendNamePreviousWeek);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVarianceFromPreviousWeek);
			
			String previousWeekrExpectedData="1/1/2023~9,459~12/25/2022~7,895~1,564~19.81|1/2/2023~11,854~12/26/2022~9,184~2,670~29.07|1/3/2023~3,847~12/27/2022~32,250~-28,403~-88.07|1/4/2023~2,246~12/28/2022~33,446~-31,200~-93.28";
			verifyTableDataWithExpected(previousWeekrExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Previous Week Data tab values!!");
			
			//Select the Previous day as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Previous Day");
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), legendNamePreviousDay);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVarianceFromPreviousDay);

			String previousDayExpectedData="1/1/2023~9,459~12/31/2022~8,635~824.0~9.543|1/2/2023~11,854~1/1/2023~9,459~2,395~25.32|1/3/2023~3,847~1/2/2023~11,854~-8,007~-67.55|1/4/2023~2,246~1/3/2023~3,847~-1,601~-41.62";
			verifyTableDataWithExpected(previousDayExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Previous Day Data tab values!!");
			
			//Select the year before last as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Year Before Last");
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), legendNameYearBeforeLast);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVarianceFromYearBeforeLast);

			String previousYearBeforeLastExpectedData="1/1/2023~9,459~1/3/2021~10,958~-1,499~-13.68|1/2/2023~11,854~1/4/2021~13,486~-1,632~-12.10|1/3/2023~3,847~1/5/2021~5,438~-1,591~-29.26|1/4/2023~2,246~1/6/2021~7,453~-5,207~-69.86";
			verifyTableDataWithExpected(previousYearBeforeLastExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Year Beforw Last Day Data tab values!!");
			
			//Select the Select Start Date as Comparison Date Range and verify 1st 4 rows of table data
			comparisionDateRange("Select Start Date");
			Thread.sleep(2000);
			getWebElementActionXpath_D("//*[@id='comparisonDateRange']//input[contains(@id,'datepicker')]").click();
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//*[@id='comparisonDateRange']//input[contains(@id,'datepicker')]").sendKeys("1/1/2022");
			getWebElementXpath_D("//*[@id='comparisonDateRange']//button[@title='Apply']").click();
			aJaxWait();
			
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), deaultLegend);
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), columnLegend);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), legendNameVariance);
			
			String dateStartWithExpectedData="1/1/2023~9,459~1/1/2022~9,357~102.0~1.090|1/2/2023~11,854~1/2/2022~10,050~1,804~17.95|1/3/2023~3,847~1/3/2022~9,711~-5,864~-60.39|1/4/2023~2,246~1/4/2022~7,099~-4,853~-68.36";
			verifyTableDataWithExpected(dateStartWithExpectedData,4,"QIDM_62_TrendAnalysisComparisonDateRanges");
			printLog("Verified for Select start date !!");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
