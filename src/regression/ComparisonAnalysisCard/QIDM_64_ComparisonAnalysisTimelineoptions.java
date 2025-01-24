package regression.ComparisonAnalysisCard;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all the timeline options available for comparison analysis card.  
 * Selects each option one by one and verifies date range on the top is as expected in both bar 
 * and pie chart.
 */

public class QIDM_64_ComparisonAnalysisTimelineoptions extends TestBase {
	
	LoginTC login = null;

	@Test
	public void comparisonAnalysisTimelineoptions() throws Throwable {
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
			
			//Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			ArrayList<String> actualXaxisLabels = new ArrayList<String>();
			actualXaxisLabels.add(sitesList[0]);
			actualXaxisLabels.add(sitesList[1]);
			
			ArrayList<String> expectedXaxisLabels = new ArrayList<String>();
			expectedXaxisLabels.add("PAMTest_Herentals, Biscuits");
			expectedXaxisLabels.add(testData.getProperty("PAMTestCapriataSaiwa"));
			
			Collections.sort(actualXaxisLabels);
			//boolean flag;
			Assert.assertTrue(actualXaxisLabels.containsAll(expectedXaxisLabels));			
			printLog("Verified the sites on the chart as "+sitesList);
			
			//veriable declarations
			String actualStartChartDate,actualEndChartDate;
			LocalDate today = LocalDate.now();
			//Select 'Today' from TimeLine options and verify the StartDate and EndDate of the bar chart
			getWebElementXpath("TimeLineToday").click();
			Thread.sleep(6000);
			
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verify the StartDate and EndDate of the bar chart for 'Today' from TimeLine options");
			Thread.sleep(2000);
			if(!checkBrowserHeadless) {
				//Select the Pie chart and verify the StartDate and EndDate of the Pie chart
				getWebElementActionXpath("PieChart").click();
				Thread.sleep(2000);
				actualStartChartDate = getWebElementXpath("PieChartStartDate").getText();
				Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today));
				actualEndChartDate = getWebElementXpath("PieChartEndDate").getText();
				Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
				printLog("Verify the StartDate and EndDate of the Pie chart for 'Today' from TimeLine options");
			}
			//Select 'Yesterday' from TimeLine options and verify the StartDate and EndDate of the Pie chart
			getWebElementXpath("TimeLineYesterday").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			today = LocalDate.now();
			if(!checkBrowserHeadless) {
				actualStartChartDate = getWebElementXpath("PieChartStartDate").getText();
				Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				actualEndChartDate = getWebElementXpath("PieChartEndDate").getText();
				Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				printLog("Verify the StartDate and EndDate of the Pie chart for 'Yesterday' from TimeLine options");
				
				//Select the Bar chart and Verify the StartDate and EndDate of the Bar chart
				getWebElementActionXpath("BarChart").click();
				Thread.sleep(2000);
				actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
				Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
				Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				printLog("Verify the StartDate and EndDate of the Bar chart for 'Yesterday' from TimeLine options ");
			}else {
				actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
				Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
				Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today.minusDays(1)));
				printLog("Verify the StartDate and EndDate of the Bar chart for 'Yesterday' from TimeLine options");
			}
			
			
			//Select 'Year to Date' from TimeLine options and verify the StartDate and EndDate of the Bar chart
			getWebElementActionXpath("TimeLineYearToDate").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfYear())));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verify the StartDate and EndDate of the Bar chart for 'Year to Date' from TimeLine options ");
			
			//Select 'Month to Date' from TimeLine options and verify the StartDate and EndDate of the Bar chart
			getWebElementActionXpath("TimeLineMonthToDate").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfMonth())));
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verify the StartDate and EndDate of the Bar chart for 'Month to Date' from TimeLine options ");
			
			//Select 'Starting from' and provide first day of month and Verify the StartDate and EndDate of the Bar chart
			getWebElementActionXpath("TimeLineStartingFrom").click();
			String startingDateToAdd=changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
			/*getWebElementActionXpath("StartingFromStartDate").click();
			getWebElementActionXpath("StartingFromStartDate").clear();
			getWebElementActionXpath("StartingFromStartDate").sendKeys(startingDateToAdd);
			Thread.sleep(2000);*/
			getWebElementActionXpath_D("//span[normalize-space()='Starting from']").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").click();
			Thread.sleep(1000);
			//pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(startingDateToAdd);
			Thread.sleep(1000);
			
			getWebElementXpath("StartingFromDateApply").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, startingDateToAdd);			
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verify the StartDate and EndDate of the Bar chart for 'Starting from' from TimeLine options");
			
			//Select the 'Fixed date' of 1/1/2023 - 1/31/2023 and Verify the StartDate and EndDate of the Bar chart
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(actualEndChartDate, testData.getProperty("FixedDateEndJan312023"));
			printLog("Verify the StartDate and EndDate of the Bar chart for 'Fixed date' from TimeLine options ");
			
			//Select the 'Last' of 12 months and include current month and verify the StartDate and EndDate of the Bar chart	
			timeinterval("12", "Months", true);
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//refreshToLoadTheChart();
			
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, changeTheDateFormat("M/d/yyyy", today.minusMonths(11).with(TemporalAdjusters.firstDayOfMonth())));
			Assert.assertEquals(actualEndChartDate, changeTheDateFormat("M/d/yyyy", today));
			printLog("Verify the StartDate and EndDate of the Bar chart for 'Last 12 Months', include current month from TimeLine options");
			
			login.logout();
			
		} catch (Throwable e) {
			takeScreenShot("QIDM_64_ComparisonAnalysisTimelineoptions");
			e.printStackTrace();
			throw e;
		}
	}
}
